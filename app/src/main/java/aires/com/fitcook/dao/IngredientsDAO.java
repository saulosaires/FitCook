package aires.com.fitcook.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import aires.com.fitcook.entity.Entity;
import aires.com.fitcook.entity.Ingredients;


public class IngredientsDAO {

	private DatabaseHelper helper;

  public IngredientsDAO(Context context) {
    this.helper=new DatabaseHelper(context);

  }

	public Ingredients fromCursor(Cursor c){

		String publicId    = c.getString(c.getColumnIndex(DatabaseHelper.KEY_INGREDIENT_PUBLICID));
		String description = c.getString(c.getColumnIndex(DatabaseHelper.KEY_INGREDIENT_DESCRIPTION));


		return new Ingredients( publicId, description);

	}

  public boolean create(Entity entity){

	  Ingredients instruction = (Ingredients) entity;

	  SQLiteDatabase db = helper.getDatabase();

	  if(instruction.getPublicId()==null)return false;

      long todo_id = db.insert(DatabaseHelper.TABLE_INGREDIENT, null, instruction.toContentValues());

	  if(todo_id==-1)
	    	return false;
	  else
	    	return true;

  }

  public Entity read(String id){

	    SQLiteDatabase db = helper.getDatabase();

	    String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_INGREDIENT+ " WHERE "
	            + DatabaseHelper.KEY_INGREDIENT_PUBLICID + " like '" + id+"'";

	    Cursor c = db.rawQuery(selectQuery, null);

	    if (c != null)
	        c.moveToFirst();

	    if(c.getCount()==0)return null;

	    Ingredients ingredients =fromCursor(c);
		c.close();

	    return ingredients;
  }

  public boolean update(Entity entity){

	  Ingredients ingredients = (Ingredients) entity;

	  SQLiteDatabase db = helper.getDatabase();

      long rows = db.update(DatabaseHelper.TABLE_INGREDIENT,
							ingredients.toContentValues(),
							DatabaseHelper.KEY_INGREDIENT_PUBLICID + " like '" + ingredients.getPublicId() + "'",
							null);

	  if(rows==0)
	    	return false;
	  else
	    	return true;

  }

  public boolean delete(String id){

	  SQLiteDatabase db = helper.getDatabase();
	  long rows =db.delete(DatabaseHelper.TABLE_INGREDIENT, DatabaseHelper.KEY_INGREDIENT_PUBLICID + " like '" + id+"'", null);

	  if(rows==0)
	    	return false;
	  else
	    	return true;

 }

 public boolean deleteAll(){

	  SQLiteDatabase db = helper.getDatabase();
	  long rows =db.delete(DatabaseHelper.TABLE_INSTRUCTION, null, null);

	  if(rows==0)
	    	return false;
	  else
	    	return true;

 }

  
}