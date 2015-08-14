package aires.com.fitcook.entity;

import android.content.ContentValues;

import java.io.Serializable;

public abstract class Entity implements Serializable{

    public abstract ContentValues toContentValues();
	
}
