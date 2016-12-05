
package aires.com.fitcook.util;

import android.content.Context;


import android.os.AsyncTask;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import aires.com.fitcook.FitCookApp;
import aires.com.fitcook.dao.RecipeDAO;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.webservice.WebService;

public class SyncUtils {

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
                        long time     = response.getLong("time");

                        if(status.equals("success")){

                            JSONArray arrayRecipe = new JSONArray(response.getString("array"));

                            List<Recipe> listRecipe= JsonUtil.parseRecipe(arrayRecipe);

                            if (listRecipe.size() > 0) {

                                RecipeDAO recipeDAO=new RecipeDAO(context);


                                for(Recipe recipe:listRecipe) {

                                    if(recipe.getAtivo()) {
                                        recipeDAO.merge(recipe);
                                    }else{
                                        recipeDAO.delete(recipe.getPublicId());
                                    }

                                }

                                FitCookApp.putSharedPreferencesValue(context,FitCookApp.TIME,time);
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


            FitCookApp.cancelAllRequests(WebService.retrieve);
            WebService.retrieveRecipes(FitCookApp.getSharedPreferencesValue(context,FitCookApp.TIME,0),wsCallBack);

            return null;
        }

    }


    public static abstract class CallBack {

        public abstract void onStart();
        public abstract void onFinished(boolean error);
    }



}
