

package aires.com.fitcook.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;


import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import aires.com.fitcook.FitCookApp;
import aires.com.fitcook.accounts.FitCookAccountService;
import aires.com.fitcook.dao.RecipeDAO;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.webservice.WebService;


public class SyncUtils {

    private static final long SYNC_FREQUENCY = 60 * 60;  // 1 hour (in seconds)
    private static final String CONTENT_AUTHORITY = FitCookApp.CONTENT_AUTHORITY;
    public static final String ACCOUNT_TYPE = "aires.com.fitcook.account";


    @TargetApi(Build.VERSION_CODES.FROYO)
    public static void CreateSyncAccount(Context context) {

        // Create account, if it's missing. (Either first run, or user has deleted account.)
        Account account = FitCookAccountService.GetAccount(ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(account, null, null)) {
            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(account, CONTENT_AUTHORITY, new Bundle(), SYNC_FREQUENCY);

        }


    }

    public static void TriggerRefresh(Context context,CallBack callBack) {

       new UpdateRecipes(callBack,context).execute();

    }

    public static class UpdateRecipes extends AsyncTask<Void,Void,List<Recipe>> {

        private Context context;
        private final CallBack callBack;

        public UpdateRecipes(CallBack callBack,Context context){

            this.context=context;
            this.callBack=callBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(callBack!=null)callBack.onStart();
        }

        @Override
        protected List<Recipe> doInBackground(Void... params) {

            WebService.CallBack wsCallBack =new WebService.CallBack() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String status = response.getString("status");

                        if(status.equals("success")){

                            JSONArray arrayRecipe = new JSONArray(response.getString("array"));

                            List<Recipe> listRecipe= JsonUtil.parseRecipe(arrayRecipe);

                            if (listRecipe.size() > 0) {

                                RecipeDAO recipeDAO=new RecipeDAO(context);
                                recipeDAO.setNew(false);

                                for(Recipe recipe:listRecipe) {

                                    if(recipe.getAtivo()) {
                                        recipeDAO.merge(recipe);
                                    }else{
                                        recipeDAO.delete(recipe.getPublicId());
                                    }

                                }

                                FitCookApp.putSharedPreferencesValue(context,FitCookApp.TIME,response.getLong("time"));
                            }

                            if(callBack!=null)callBack.onFinished(false);

                        }else  if(status.equals("error")){
                            if(callBack!=null)callBack.onFinished(true);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(callBack!=null)callBack.onFinished(true);

                }
            };


            FitCookApp.getInstance().cancelAllRequests(WebService.retrieve);
            WebService.retrieveRecipes(FitCookApp.getSharedPreferencesValue(context,FitCookApp.TIME,0),wsCallBack);


            return null;
        }

    }


    public static abstract class CallBack {

        public abstract void onStart();
        public abstract void onFinished(boolean error);
    }



}
