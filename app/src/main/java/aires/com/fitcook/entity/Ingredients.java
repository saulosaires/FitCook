package aires.com.fitcook.entity;

import android.content.ContentValues;

import aires.com.fitcook.dao.DatabaseHelper;

/**
 * Created by Aires on 10/08/2015.
 */
public class Ingredients  extends Entity{

    private String publicId;
    private String description;

    public Ingredients(String publicId, String description) {

        this.publicId = publicId;
        this.description = description;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContentValues toContentValues(){

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.KEY_INGREDIENT_PUBLICID,publicId);
        values.put(DatabaseHelper.KEY_INGREDIENT_DESCRIPTION,description);

        return values;

    }
}
