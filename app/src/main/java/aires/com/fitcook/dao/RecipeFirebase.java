package aires.com.fitcook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import aires.com.fitcook.entity.Category;
import aires.com.fitcook.entity.Recipe;


public class RecipeFirebase {


    private DatabaseReference userRef;

    public RecipeFirebase() {


        this.userRef = FirebaseDatabase.getInstance().getReference("recipes");
        this.userRef.keepSynced(true);
    }


    public Task<Void> persist(Recipe recipe){

        return userRef.child(recipe.getPublicId()).setValue(recipe);

    }

    public void read(final CallBack callBack){

        userRef.orderByChild("time").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Recipe> lista = new ArrayList<Recipe>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    lista.add(postSnapshot.getValue(Recipe.class));

                }

                Collections.sort(lista, new Comparator<Recipe>() {
                    @Override
                    public int compare(Recipe recipe1, Recipe recipe2)
                    {

                        return  (int)(recipe2.getTime()-recipe1.getTime());
                    }
                });


                callBack.onResponse(lista);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void readByCategory(final Category category,final CallBack callBack){

        userRef.orderByChild("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Recipe> lista = new ArrayList<Recipe>();

                int value= (int) Math.pow(2, category.getBitPosition());

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    Recipe recipe = postSnapshot.getValue(Recipe.class);

                    if((recipe.getCategory()&value)==value){
                        lista.add(recipe);
                    }


                }
                callBack.onResponse(lista);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void search(String name,final CallBack callBack){

        userRef.orderByChild("name").startAt(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Recipe> lista = new ArrayList<Recipe>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    lista.add(postSnapshot.getValue(Recipe.class));

                }
                callBack.onResponse(lista);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void read(String id,final CallBack callBack){

        userRef.orderByChild("publicId").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    Recipe r=postSnapshot.getValue(Recipe.class);
                    callBack.onResponse(r);
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static abstract class CallBack {

        public abstract void onResponse(Object obj);
        public abstract void onErrorResponse(DatabaseError databaseError);
    }

}
