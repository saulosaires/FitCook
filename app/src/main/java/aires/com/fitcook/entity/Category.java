package aires.com.fitcook.entity;

import android.content.ContentValues;

import aires.com.fitcook.dao.DatabaseHelper;

public class Category extends Entity{

    private String publicId;
    private String description;

    public Category(String publicId, String description) {
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

        values.put(DatabaseHelper.KEY_CATEGORY_PUBLICID,publicId);
        values.put(DatabaseHelper.KEY_CATEGORY_DESCRIPTION,description);

        return values;

    }
}
