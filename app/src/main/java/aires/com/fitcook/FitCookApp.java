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
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;

import aires.com.fitcook.entity.Category;


public class FitCookApp extends Application {

    // The following line should be changed to include the correct property id.
    private static final String PROPERTY_ID = "UA-42957667-5";


    public static final String CONTENT_AUTHORITY = "aires.com.fitcook";


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

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


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
