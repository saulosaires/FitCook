package aires.com.fitcook;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;


import java.util.HashMap;

import aires.com.fitcook.entity.Category;


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


        mapCategory.put(R.id.category_drink, new Category(R.id.category_drink, R.drawable.drink_big, 0, getResources().getString(R.string.category_drink)));
        mapCategory.put(R.id.category_cake,  new Category(R.id.category_cake,  R.drawable.cake_big,  1, getResources().getString(R.string.category_cake)));
        mapCategory.put(R.id.category_meat,  new Category(R.id.category_meat,  R.drawable.meat_big,  2, getResources().getString(R.string.category_meat)));
        mapCategory.put(R.id.category_snacks,new Category(R.id.category_snacks,R.drawable.snacks_big,4, getResources().getString(R.string.category_snack)));
        mapCategory.put(R.id.category_pasta, new Category(R.id.category_pasta, R.drawable.pasta_big, 5, getResources().getString(R.string.category_pasta)));
        mapCategory.put(R.id.category_salad, new Category(R.id.category_salad, R.drawable.salad_big, 8, getResources().getString(R.string.category_salad)));

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
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
            mRequestQueue = Volley.newRequestQueue(this, null);
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
