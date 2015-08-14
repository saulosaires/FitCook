package aires.com.fitcook.webservice;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import aires.com.fitcook.FitCookApp;

public class WebService {

     public static final String url="http://fitcook-pingy.rhcloud.com/fitcook/service/";

     public static final String retrieve="recipe/retrieve";


     public static void retrieveRecipes(final CallBack callBack){

         if(callBack==null) {
             throw new IllegalArgumentException("callBack cant be null");
         }

         StringBuilder mUrl = new StringBuilder(url);

         mUrl.append(retrieve);

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

         FitCookApp.getInstance().addRequest(jsonObjectRequest, retrieve);
     }



    public static abstract class CallBack {

        public abstract void onResponse(JSONObject response);
        public abstract void onErrorResponse(VolleyError error);
    }


}
