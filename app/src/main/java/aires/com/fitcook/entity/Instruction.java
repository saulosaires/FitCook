package aires.com.fitcook.entity;

import android.content.ContentValues;

import aires.com.fitcook.dao.DatabaseHelper;

/**
 * Created by Aires on 10/08/2015.
 */
public class Instruction  extends Entity{

    private Long basis;
    private String publicId;
    private String description;

    public Instruction(Long basis, String publicId, String description) {

        this.basis = basis;
        this.publicId = publicId;
        this.description = description;
    }

    public Long getBasis() {
        return basis;
    }

    public void setBasis(Long basis) {
        this.basis = basis;
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

        values.put(DatabaseHelper.KEY_INSTRUCTION_PUBLICID,publicId);
        values.put(DatabaseHelper.KEY_INSTRUCTION_BASIS,basis);
        values.put(DatabaseHelper.KEY_INSTRUCTION_DESCRIPTION,description);

        return values;

    }
}
