package aires.com.fitcook;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aires.com.fitcook.entity.Category;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.webservice.OkHttpStack;


public class FitCookApp extends Application {

    // The following line should be changed to include the correct property id.
    private static final String PROPERTY_ID = "UA-42957667-5";


    public static final String CONTENT_AUTHORITY = "aires.com.fitcook";

    public static HashMap<Integer,Category> mapCategory = new HashMap<Integer,Category>();

    private Tracker mTracker;

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


        mapCategory.put(R.id.category_drink, new Category(R.id.category_drink, R.drawable.drink_big, 0, "Bebidas"));
        mapCategory.put(R.id.category_cake,  new Category(R.id.category_cake,  R.drawable.cake_big,  1, "Bolos e Tortas"));
        mapCategory.put(R.id.category_meat,  new Category(R.id.category_meat,  R.drawable.meat_big,  2, "Carnes e Aves"));
        mapCategory.put(R.id.category_candy, new Category(R.id.category_candy, R.drawable.candy_big, 3, "Doces e Sobremesas"));
        mapCategory.put(R.id.category_snacks,new Category(R.id.category_snacks,R.drawable.snacks_big,4, "Lanches"));
        mapCategory.put(R.id.category_pasta, new Category(R.id.category_pasta, R.drawable.pasta_big, 5, "Massa"));
        mapCategory.put(R.id.category_bread, new Category(R.id.category_bread, R.drawable.bread_big, 6, "Pães"));
        mapCategory.put(R.id.category_fish,  new Category(R.id.category_fish,  R.drawable.fish_big,  7, "Peixe o frutos do Mar"));
        mapCategory.put(R.id.category_salad, new Category(R.id.category_salad, R.drawable.salad_big, 8, "Saladas e Molhos"));
        mapCategory.put(R.id.category_soup,  new Category(R.id.category_soup,  R.drawable.soup_big,  9, "Sopa"));
        mapCategory.put(R.id.category_juice, new Category(R.id.category_juice, R.drawable.juice_big, 10,"Sucos"));

    }

    synchronized public Tracker getDefaultTracker() {

        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(PROPERTY_ID);
        }
        return mTracker;

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

    public static String TIME="TIME";

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

    public static Long getSharedPreferencesValue(Context context,String name,long defValue){

        if(context!=null)
            return  PreferenceManager.getDefaultSharedPreferences(context).getLong(name, defValue);
        else
            return null;

    }

    public static boolean putSharedPreferencesValue(Context context,String key,long value){

        if(context==null) return false;

        return PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putLong(key, value)
                .commit();


    }


}
