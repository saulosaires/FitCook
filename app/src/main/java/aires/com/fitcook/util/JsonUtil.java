package aires.com.fitcook.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aires.com.fitcook.FitCookApp;
import aires.com.fitcook.entity.Category;
import aires.com.fitcook.entity.Recipe;

public class JsonUtil {

    public static List<Recipe> parseRecipe(JSONArray arrayRecipe) throws JSONException {

        List<Recipe> listRecipe= new ArrayList<Recipe>();

        for(int i=0;i<arrayRecipe.length();i++){

            JSONObject obj =arrayRecipe.getJSONObject(i);

            Boolean ativo        =obj.getBoolean("ativo");
            String publicId      =obj.getString("_id");
            long time            =obj.getLong("time");

            JSONObject objRecipe =obj.getJSONObject("recipe");

            String name          =objRecipe.getString("name");
            String url           =objRecipe.getJSONObject("file").getString("url");

            String description   =objRecipe.getString("description");
            String timeToPrepare =objRecipe.getString("timeToPrepare");
            String servings      =objRecipe.getString("servings");
            int category         =objRecipe.getInt("category");
            String  ingredients  =objRecipe.getString("ingredient");
            String  instruction  =objRecipe.getString("instruction");



            Recipe recipe = new  Recipe(publicId,
                                         name,
                                         url,
                                         description,
                                         timeToPrepare,
                                         servings,
                                         false,
                                         category,
                                         ingredients,
                                         instruction,
                                         ativo,
                                         time);

            listRecipe.add(recipe);
        }

        return listRecipe;
    }

    public static List<String> parseList( JSONArray ingredients) throws JSONException {

        List<String> listIngredients= new ArrayList<String>();

        for(int i = 0; i < ingredients.length(); i++)
        {
            listIngredients.add(ingredients.get(i).toString());
        }

        return listIngredients;
    }



}
