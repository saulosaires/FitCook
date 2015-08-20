package aires.com.fitcook.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import aires.com.fitcook.entity.Category;
import aires.com.fitcook.entity.Entity;

import aires.com.fitcook.entity.Recipe;

public class RecipeDAO {

	private DatabaseHelper helper;

  public RecipeDAO(Context context) {
	  this.helper = new DatabaseHelper(context);

  }

	public Recipe fromCursor(Cursor c){

		String id            = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RECIPE_PUBLICID));
		String name          = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RECIPE_NAME));
		String url           = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RECIPE_URL));
		String description   = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RECIPE_DESCRIPTION));
		String timeToPrepare = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RECIPE_TIME_TO_PREPARE));
		String servings      = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RECIPE_SERVINGS));
		Integer fav          = c.getInt(c.getColumnIndex(DatabaseHelper.KEY_RECIPE_FAV));
        Integer category     = c.getInt(c.getColumnIndex(DatabaseHelper.KEY_RECIPE_CAT));
		String instructions  = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RECIPE_INST));
		String ingredients   = c.getString(c.getColumnIndex(DatabaseHelper.KEY_RECIPE_INGR));

		Boolean favorite=false;

		if(fav!=null && fav.intValue()==1)favorite=true;

		return new Recipe(id, name,url,description,timeToPrepare,servings,favorite,category,ingredients,instructions);

	}

  public boolean create(Recipe recipe){

	  SQLiteDatabase db = helper.getDatabase();

	  if(recipe==null)return false;

      long todo_id = db.insert(DatabaseHelper.TABLE_RECIPE, null, recipe.toContentValues());
 
	  if(todo_id==-1)
	    	return false;
	  else
	    	return true;
	  
  }

  public boolean merge(Recipe recipe){

      if(recipe==null || recipe.getPublicId()==null || recipe.getPublicId().equals(""))return false;


      Recipe mRecipe= read(recipe.getPublicId());

      if(mRecipe==null){

          return  create(recipe);
      }else{
          return update(recipe);
      }


  }

  public Recipe read(String publicId){

	    SQLiteDatabase db = helper.getDatabase();

	    String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_RECIPE + " WHERE "
	            + DatabaseHelper.KEY_RECIPE_PUBLICID + " like '" + publicId+"'";

	    Cursor c = db.rawQuery(selectQuery, null);

	    if (c != null)
	        c.moveToFirst();

	    if(c.getCount()==0)return null;

		Recipe recipe= fromCursor(c);
	  	c.close();

	  return recipe;

  }

  
  public boolean update(Recipe recipe){

	  SQLiteDatabase db = helper.getDatabase();

      long rows = db.update(DatabaseHelper.TABLE_RECIPE,
			  			    recipe.toContentValues(),
			  			    DatabaseHelper.KEY_RECIPE_PUBLICID + " like '"+recipe.getPublicId()+"'",null);
 
	  if(rows==0)
	    	return false;
	  else
	    	return true;
	  
  }

  public boolean delete(String id){
 
	  SQLiteDatabase db = helper.getDatabase();
	  long rows =db.delete(DatabaseHelper.TABLE_RECIPE, DatabaseHelper.KEY_RECIPE_PUBLICID + " like '" + id + "'", null);

	  if(rows==0)
	    	return false;
	  else
	    	return true;

 }
  
 public boolean deleteAll(){
	  
	  SQLiteDatabase db = helper.getDatabase();
	  long rows =db.delete(DatabaseHelper.TABLE_RECIPE, null, null);

	  if(rows==0)
	    	return false;
	  else
	    	return true;

 }

  public List<Recipe> getAll(){

		SQLiteDatabase db = helper.getDatabase();

		String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_RECIPE;

		Cursor c = db.rawQuery(selectQuery,  new String[] {});

		if (c != null)
			c.moveToFirst();

		if (c.getCount() == 0)
			return null;

		List<Recipe> listRecipe = new ArrayList<Recipe>();

		while (!c.isAfterLast()) {

			listRecipe.add(	fromCursor(c));
			c.moveToNext();
		}
		c.close();

		return listRecipe;
  }

	public List<Recipe> readFav(){

		SQLiteDatabase db = helper.getDatabase();

		String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_RECIPE + " WHERE "
				+ DatabaseHelper.KEY_RECIPE_FAV + "=1";

		Cursor c = db.rawQuery(selectQuery,  new String[] {});

		if (c != null)
			c.moveToFirst();

		if (c.getCount() == 0)
			return null;

		List<Recipe> listRecipe = new ArrayList<Recipe>();

		while (!c.isAfterLast()) {

			listRecipe.add(fromCursor(c));
			c.moveToNext();
		}
		c.close();

		return listRecipe;
	}

	public List<Recipe> readByCategory(Category category){

		double value=Math.pow(2, category.getBitPosition());

		SQLiteDatabase db = helper.getDatabase();

		String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_RECIPE + " WHERE "
				+ "("+DatabaseHelper.KEY_RECIPE_CAT + "&"+value+")="+value;

		Cursor c = db.rawQuery(selectQuery,  new String[] {});

		if (c != null)
			c.moveToFirst();

		if (c.getCount() == 0)
			return null;

		List<Recipe> listRecipe = new ArrayList<Recipe>();

		while (!c.isAfterLast()) {

			listRecipe.add(fromCursor(c));
			c.moveToNext();
		}
		c.close();

		return listRecipe;
	}

}