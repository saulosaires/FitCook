package aires.com.fitcook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import aires.com.fitcook.dao.RecipeFirebase;
import aires.com.fitcook.entity.Category;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.fragment.EmptyFragment;
import aires.com.fitcook.fragment.RecipeFragment;
import aires.com.fitcook.util.AnalyticsUtil;

public class CategoryActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_SEARCH="EXTRA_CATEGORY_SEARCH";
    public static final String EXTRA_CATEGORY_ID="EXTRA_CATEGORY_ID";
    public static final String FRAGMENT_CAT = "FRAGMENT_CAT";

    private RecipeFirebase recipeDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        AnalyticsUtil.send(this, "CategoryActivity");

        setUpActionBar();
        recipeDAO=new RecipeFirebase();
        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_CATEGORY_ID,-1);
        String search = intent.getStringExtra(EXTRA_CATEGORY_SEARCH);

        if(id>0) {
         //   handleCategory(id);
        }else{
            handleSearch(search);
        }


    }

    private void handleSearch(String search){

        setTitle(getResources().getString(R.string.search_pre)+search);

        recipeDAO.search(search,new RecipeFirebase.CallBack() {
            @Override
            public void onResponse(Object obj) {

                List<Recipe> lista= (List<Recipe>) obj;
                showRecipeFragment(lista);
            }

            @Override
            public void onErrorResponse(DatabaseError databaseError) {

            }
        });





    }


    public void setUpActionBar(){

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {

            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

    }



    private void showRecipeFragment(List<Recipe> listRecipe){

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeFragment recipeFragment = (RecipeFragment) fragmentManager.findFragmentByTag(FRAGMENT_CAT);

        if(recipeFragment!=null){
            recipeFragment.init(listRecipe);
            recipeFragment.update();
            fragmentManager.beginTransaction().show(recipeFragment).commit();
        }else{
            fragmentManager.beginTransaction().replace(R.id.container, RecipeFragment.newInstance((listRecipe)),FRAGMENT_CAT)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }


}
