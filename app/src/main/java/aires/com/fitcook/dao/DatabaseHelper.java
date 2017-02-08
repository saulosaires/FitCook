package aires.com.fitcook.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "FITCOOK_DB.db";

    public static final String TABLE_RECIPE = "TABLE_RECIPE";
    public static final String KEY_RECIPE_PUBLICID = "publicid";
    public static final String KEY_RECIPE_NAME = "name";
    public static final String KEY_RECIPE_URL = "url";
    public static final String KEY_RECIPE_DESCRIPTION = "description";
    public static final String KEY_RECIPE_TIME_TO_PREPARE = "timeToPrepare";
    public static final String KEY_RECIPE_SERVINGS = "servings";
    public static final String KEY_RECIPE_FAV = "fav";
    public static final String KEY_RECIPE_CAT = "category";
    public static final String KEY_RECIPE_INST = "instruction";
    public static final String KEY_RECIPE_INGR = "ingredient";
    public static final String KEY_RECIPE_TIME    = "time";

    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE "+ TABLE_RECIPE+"("+
            KEY_RECIPE_PUBLICID+" VARCHAR(100),  "+
            KEY_RECIPE_NAME+" VARCHAR(100),"+
            KEY_RECIPE_URL+" VARCHAR(500),"+
            KEY_RECIPE_DESCRIPTION+" VARCHAR(500),"+
            KEY_RECIPE_TIME_TO_PREPARE+" VARCHAR(50),"+
            KEY_RECIPE_SERVINGS+" VARCHAR(50),"+
            KEY_RECIPE_INST+" VARCHAR(4000),"+
            KEY_RECIPE_INGR+" VARCHAR(4000),"+
            KEY_RECIPE_CAT+" INTEGER,"+
            KEY_RECIPE_TIME + " DOUBLE,"+
            KEY_RECIPE_FAV+" INTEGER)";



    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_TABLE_RECIPE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {

            db.execSQL("CREATE TABLE my_table_temp as  SELECT "+
                    KEY_RECIPE_PUBLICID+" , " +
                    KEY_RECIPE_NAME+" , " +
                    KEY_RECIPE_URL+" , " +
                    KEY_RECIPE_DESCRIPTION+" , " +
                    KEY_RECIPE_TIME_TO_PREPARE+" , " +
                    KEY_RECIPE_SERVINGS+" , " +
                    KEY_RECIPE_INST+" , " +
                    KEY_RECIPE_INGR+" , " +
                    KEY_RECIPE_CAT+" , " +
                    KEY_RECIPE_FAV+
                    " FROM "+TABLE_RECIPE+" ");

            db.execSQL("ALTER TABLE my_table_temp ADD COLUMN " + KEY_RECIPE_TIME + " DOUBLE");

            db.execSQL("DROP TABLE "+TABLE_RECIPE);

            db.execSQL("ALTER TABLE my_table_temp RENAME TO "+TABLE_RECIPE);
        }

    }

    public SQLiteDatabase getDatabase() {
        return this.getReadableDatabase();
    }



}