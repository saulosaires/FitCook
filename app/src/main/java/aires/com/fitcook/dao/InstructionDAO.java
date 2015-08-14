package aires.com.fitcook.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import aires.com.fitcook.entity.Entity;
import aires.com.fitcook.entity.Instruction;

public class InstructionDAO {

	private DatabaseHelper helper;

  public InstructionDAO(Context context) {
    this.helper=new DatabaseHelper(context);
  
  }

	public Instruction fromCursor(Cursor c){

		String publicId    = c.getString(c.getColumnIndex(DatabaseHelper.KEY_INSTRUCTION_PUBLICID));
		String description = c.getString(c.getColumnIndex(DatabaseHelper.KEY_INSTRUCTION_DESCRIPTION));
		Long basis         = c.getLong(c.getColumnIndex(DatabaseHelper.KEY_INSTRUCTION_BASIS));

		Boolean favorite=false;

		return new Instruction(basis, publicId, description);

	}

  public boolean create(Entity entity){
	  
	  Instruction instruction = (Instruction) entity;

	  SQLiteDatabase db = helper.getDatabase();

	  if(instruction.getPublicId()==null)return false;

      long todo_id = db.insert(DatabaseHelper.TABLE_INSTRUCTION, null, instruction.toContentValues());
 
	  if(todo_id==-1)
	    	return false;
	  else
	    	return true;
	  
  }
 
  public Entity read(String id){

	    SQLiteDatabase db = helper.getDatabase();
	  
	    String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_INSTRUCTION+ " WHERE "
	            + DatabaseHelper.KEY_INGREDIENT_PUBLICID + " like '" + id+"'";
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	        c.moveToFirst();
	 
	    if(c.getCount()==0)return null;

	  	Instruction instruction =fromCursor(c);
		c.close();

	    return instruction;
  }
  
  public boolean update(Entity entity){

	  Instruction instruction = (Instruction) entity;

	  SQLiteDatabase db = helper.getDatabase();

      long rows = db.update(DatabaseHelper.TABLE_INSTRUCTION,
			  				instruction.toContentValues(),
			  				DatabaseHelper.KEY_INSTRUCTION_PUBLICID + " like '"+instruction.getPublicId()+"'",
			  				null);
 
	  if(rows==0)
	    	return false;
	  else
	    	return true;
	  
  }

  public boolean delete(String id){
 
	  SQLiteDatabase db = helper.getDatabase();
	  long rows =db.delete(DatabaseHelper.TABLE_INSTRUCTION, DatabaseHelper.KEY_INSTRUCTION_PUBLICID + " like '" + id+"'", null);

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