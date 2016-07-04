package aires.com.fitcook.entity;

import android.content.ContentValues;
import java.util.List;

import aires.com.fitcook.FitCookApp;
import aires.com.fitcook.dao.DatabaseHelper;
import aires.com.fitcook.util.BitWiseUtil;

public class Recipe  extends Entity{

    private String publicId;
    private String name;
    private String url;
    private String urlSmall;
    private String description;
    private String timeToPrepare;
    private String servings;
    private Boolean favorite;
    private int category;
    private String ingredients;
    private String instruction;
    private Boolean ativo;
    private Boolean novo;

    public Recipe(String publicId,
                       String name,
                       String url,
                       String description,
                       String timeToPrepare,
                       String servings,
                       Boolean favorite,
                       int category,
                       String ingredients,
                       String instruction) {

        this.publicId = publicId;
        this.name = name;
        this.url = url;
        this.description = description;
        this.timeToPrepare = timeToPrepare;
        this.servings = servings;
        this.favorite = favorite;
        this.category=category;
        this.ingredients=ingredients;
        this.instruction=instruction;

    }

    public Recipe(String publicId,
                  String name,
                  String url,
                  String description,
                  String timeToPrepare,
                  String servings,
                  Boolean favorite,
                  int category,
                  String ingredients,
                  String instruction,
                  Boolean ativo,
                  Boolean novo) {

        this.publicId = publicId;
        this.name = name;
        this.url = url;
        this.urlSmall =url.replace("upload/","upload/c_scale,h_200/f_auto/");
        this.description = description;
        this.timeToPrepare = timeToPrepare;
        this.servings = servings;
        this.favorite = favorite;
        this.category=category;
        this.ingredients=ingredients;
        this.instruction=instruction;
        this.ativo=ativo;
        this.novo=novo;

    }

    public String getUrlSmall() {
        return urlSmall;
    }

    public void setUrlSmall(String urlSmall) {
        this.urlSmall = urlSmall;
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

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getNovo() {
        return novo;
    }

    public void setNovo(Boolean novo) {
        this.novo = novo;
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
        values.put(DatabaseHelper.KEY_RECIPE_FAV, favorite?1:0);
        values.put(DatabaseHelper.KEY_RECIPE_CAT, category);
        values.put(DatabaseHelper.KEY_RECIPE_NEW, novo?1:0);

        return values;


    }
}
