package aires.com.fitcook;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.webservice.OkHttpStack;


public class FitCookApp extends Application {

    private List<Recipe> listRecipe;

    private static FitCookApp mInstance;
    private RequestQueue mRequestQueue;

    public static FitCookApp getInstance()
    {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public void setRecipeList(List<Recipe> listRecipe){
        this.listRecipe=listRecipe;
    }

    public List<Recipe> getRecipeList(){
        return listRecipe;
    }

    public RequestQueue getVolleyRequestQueue()
    {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(this, new OkHttpStack(new OkHttpClient()));
        }
        return mRequestQueue;
    }

    private static void addRequest(Request<?> request)
    {
        getInstance().getVolleyRequestQueue().add(request);
    }

    public static void addRequest(Request<?> request, String tag)
    {
        request.setTag(tag);
        addRequest(request);
    }

    public static void cancelAllRequests(String tag)
    {
        if (getInstance().getVolleyRequestQueue() != null)
        {
            getInstance().getVolleyRequestQueue().cancelAll(tag);
        }
    }

    public static String FIRST_ACCESS="FIRST_ACCESS";

    public static Boolean getSharedPreferencesValue(Context context,String name,boolean defValue){

        if(context!=null)
            return  PreferenceManager.getDefaultSharedPreferences(context).getBoolean(name, defValue);
        else
            return null;

    }

    public static boolean putSharedPreferencesValue(Context context,String key,Boolean value){

        if(context==null) return false;

        return PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(key, value)
                .commit();


    }




}
