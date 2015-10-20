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

            String publicId      =obj.getString("publicId");
            String name          =obj.getString("name");
            String url           =obj.getJSONObject("file").getString("url");
            String description   =obj.getString("description");
            String timeToPrepare =obj.getString("timeToPrepare");
            String servings      =obj.getString("servings");
            int category         =obj.getInt("category");
            String  ingredients  =obj.getString("ingredients");
            String  instruction  =obj.getString("instructions");
            Boolean ativo        =obj.getBoolean("ativo");

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
                                         ativo,true);

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

    public static List<Category> parseCategory(JSONArray category) throws JSONException {

        List<Category> listCategory= new ArrayList<Category>();

        for(int i = 0; i < category.length(); i++) {

            listCategory.add( FitCookApp.mapCategory.get(  category.get(i).toString()));

        }

        return listCategory;
    }

}
