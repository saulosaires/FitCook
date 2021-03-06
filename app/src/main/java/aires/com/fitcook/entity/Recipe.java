package aires.com.fitcook.entity;

import android.content.ContentValues;
import java.util.List;

import aires.com.fitcook.FitCookApp;
import aires.com.fitcook.dao.DatabaseHelper;
import aires.com.fitcook.util.BitWiseUtil;

public class Recipe  extends Entity {

    private String publicId;
    private String name;
    private String url;
    private String description;
    private String timeToPrepare;
    private String servings;
    private int category;
    private String ingredients;
    private String instruction;
    private double time;

    private Boolean favorite;

    public Recipe() {
    }

    public Recipe(String publicId, String name, String url, String description, String timeToPrepare, String servings, int category, String ingredients, String instruction) {
        this.publicId = publicId;
        this.name = name;
        this.url = url;
        this.description = description;
        this.timeToPrepare = timeToPrepare;
        this.servings = servings;
        this.category = category;
        this.ingredients = ingredients;
        this.instruction = instruction;

    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeToPrepare() {
        return timeToPrepare;
    }

    public void setTimeToPrepare(String timeToPrepare) {
        this.timeToPrepare = timeToPrepare;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public ContentValues toContentValues(){

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.KEY_RECIPE_PUBLICID,publicId);
        values.put(DatabaseHelper.KEY_RECIPE_NAME,name);
        values.put(DatabaseHelper.KEY_RECIPE_URL, url);
        values.put(DatabaseHelper.KEY_RECIPE_DESCRIPTION,description);
        values.put(DatabaseHelper.KEY_RECIPE_TIME_TO_PREPARE,timeToPrepare);
        values.put(DatabaseHelper.KEY_RECIPE_SERVINGS, servings);
        values.put(DatabaseHelper.KEY_RECIPE_INST, instruction);
        values.put(DatabaseHelper.KEY_RECIPE_INGR, ingredients);
        values.put(DatabaseHelper.KEY_RECIPE_CAT, category);

        return values;


    }

}
