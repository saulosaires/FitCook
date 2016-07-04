package aires.com.fitcook.webservice;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import aires.com.fitcook.FitCookApp;

public class WebService {

     public static final String url="http://mean-pingy.rhcloud.com/api/";

     public static final String retrieve="retrieveApp";


     public static void retrieveRecipes(long time,final CallBack callBack){

         if(callBack==null) {
             throw new IllegalArgumentException("callBack cant be null");
         }

         StringBuilder mUrl = new StringBuilder(url);

         mUrl.append(retrieve).append("?time=").append(time);

         Log.v("WebService",mUrl.toString());

         JsonObjectRequest jsonObjectRequest =
                 new JsonObjectRequest(Request.Method.GET, mUrl.toString(), new Response.Listener<JSONObject>()
                 {
                     @Override
                     public void onResponse(JSONObject response)
                     {
                         callBack.onResponse(response);
                     }
                 },
                         new Response.ErrorListener()
                         {
                             @Override
                             public void onErrorResponse(VolleyError error)
                             {
                                 callBack.onErrorResponse(error);
                             }
                         });

         FitCookApp.addRequest(jsonObjectRequest, retrieve);
     }



    public static abstract class CallBack {

        public abstract void onResponse(JSONObject response);
        public abstract void onErrorResponse(VolleyError error);
    }


}
