package aires.com.fitcook.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import aires.com.fitcook.entity.Category;
import aires.com.fitcook.entity.Entity;


public class CategoryDAO{

	private DatabaseHelper helper;

  public CategoryDAO(Context context) {
    this.helper=new DatabaseHelper(context);
  }

  public Category fromCursor(Cursor c){

	String publicId    = c.getString(c.getColumnIndex(DatabaseHelper.KEY_CATEGORY_PUBLICID));
	String description = c.getString(c.getColumnIndex(DatabaseHelper.KEY_CATEGORY_DESCRIPTION));

	return new Category( publicId, description);

  }

  public boolean create(Entity entity){
	  
	  final Category category = (Category) entity;

	  SQLiteDatabase db = helper.getDatabase();

	  if(category.getPublicId()==null)return false;

      long todo_id = db.insert(DatabaseHelper.TABLE_CATEGORY, null, category.toContentValues());
 
	  if(todo_id==-1)
	    	return false;
	  else
	    	return true;
	  
  }
 
  public Entity read(String id){

	    SQLiteDatabase db = helper.getDatabase();
	  
	    String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_CATEGORY + " WHERE "
	            + DatabaseHelper.KEY_CATEGORY_PUBLICID + " like '" + id+"'";
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	        c.moveToFirst();

	    Category category = fromCursor(c);
	    c.close();

	    return category;
  }
  

  
  public boolean update(Entity entity){
	  
	  final Category category = (Category) entity;

	  SQLiteDatabase db = helper.getDatabase();

      long rows = db.update(DatabaseHelper.TABLE_CATEGORY,
			  				category.toContentValues(),
			  				DatabaseHelper.KEY_CATEGORY_PUBLICID + " like '"+category.getPublicId()+"'",null);
 
	  if(rows==0)
	    	return false;
	  else
	    	return true;
	  
  }

  public boolean delete(String id){
 
	  SQLiteDatabase db = helper.getDatabase();
	  long rows =db.delete(DatabaseHelper.TABLE_CATEGORY, DatabaseHelper.KEY_CATEGORY_PUBLICID + " like '" + id+"'", null);

	  if(rows==0)
	    	return false;
	  else
	    	return true;

 }
  
 public boolean deleteAll(){
	  
	  SQLiteDatabase db = helper.getDatabase();
	  long rows =db.delete(DatabaseHelper.TABLE_CATEGORY, null, null);

	  if(rows==0)
	    	return false;
	  else
	    	return true;

 }

  public List<Category> getAll(){

		SQLiteDatabase db = helper.getDatabase();

		String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_CATEGORY;

		Cursor c = db.rawQuery(selectQuery,  new String[] {});

		if (c != null)
			c.moveToFirst();

		if (c.getCount() == 0)
			return null;

		List<Category> listTag = new ArrayList<Category>();

		while (!c.isAfterLast()) {

			listTag.add(fromCursor(c));
			c.moveToNext();

		}
		c.close();

		return listTag;
  }  
  
}