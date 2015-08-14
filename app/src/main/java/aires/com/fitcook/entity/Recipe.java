package aires.com.fitcook.entity;

import android.content.ContentValues;

import java.util.List;
import java.util.Set;

import aires.com.fitcook.dao.DatabaseHelper;

public class Recipe  extends Entity{

    private String publicId;
    private String name;
    private String url;
    private String description;
    private String timeToPrepare;
    private String servings;
    private Boolean favorite;

    private Set<Category> categories;

    private List<Ingredients> ingredientsList;
    private List<Instruction> instructionList;

    public Recipe(String publicId,
                  String name,
                  String url,
                  String description,
                  String timeToPrepare,
                  String servings,
                  Boolean favorite,
                  Set<Category> categories,
                  List<Ingredients> ingredientsList,
                  List<Instruction> instructionList) {

        this.publicId = publicId;
        this.name = name;
        this.url = url;
        this.description = description;
        this.timeToPrepare = timeToPrepare;
        this.servings = servings;
        this.favorite = favorite;
        this.categories = categories;
        this.ingredientsList=ingredientsList;
        this.instructionList=instructionList;

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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<Instruction> getInstructionList() {
        return instructionList;
    }

    public void setInstructionList(List<Instruction> instructionList) {
        this.instructionList = instructionList;
    }

    public ContentValues toContentValues(){

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.KEY_RECIPE_PUBLICID,publicId);
        values.put(DatabaseHelper.KEY_RECIPE_NAME,name);
        values.put(DatabaseHelper.KEY_RECIPE_URL, url);
        values.put(DatabaseHelper.KEY_RECIPE_DESCRIPTION,description);
        values.put(DatabaseHelper.KEY_RECIPE_TIME_TO_PREPARE,timeToPrepare);
        values.put(DatabaseHelper.KEY_RECIPE_SERVINGS, servings);
        values.put(DatabaseHelper.KEY_RECIPE_FAV, favorite?1:0);

        return values;


    }
}
