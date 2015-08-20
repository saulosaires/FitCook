package aires.com.fitcook.entity;

import android.content.ContentValues;

import aires.com.fitcook.dao.DatabaseHelper;

public class Category {

    private int id;
    private int bitPosition;
    private String description;
    private int icon;

    public Category(int id,int icon,int bitPosition,String description) {
        this.id=id;
        this.icon=icon;
        this.bitPosition=bitPosition;
        this.description = description;
    }

    public int getBitPosition() {
        return bitPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBitPosition(int bitPosition) {
        this.bitPosition = bitPosition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
