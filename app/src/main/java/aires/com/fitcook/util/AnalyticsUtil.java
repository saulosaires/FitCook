package aires.com.fitcook.util;

import android.content.Context;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import aires.com.fitcook.FitCookApp;


/**
 * Created by saulo on 04/04/2016.
 */
public class AnalyticsUtil {

    public static void send(Context context, String screenName){

        Tracker t = ((FitCookApp) context.getApplicationContext()).getDefaultTracker();
        t.setScreenName(screenName);
        t.send(new HitBuilders.AppViewBuilder().build());

    }


}