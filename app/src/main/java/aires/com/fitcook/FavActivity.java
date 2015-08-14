package aires.com.fitcook;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import java.util.List;
import aires.com.fitcook.dao.RecipeDAO;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.fragment.EmptyFragment;
import aires.com.fitcook.fragment.RecipeFragment;

public class FavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        setUpactionBar();

        RecipeDAO recipeDAO = new RecipeDAO(this);

        List<Recipe> listRecipe = recipeDAO.readFav();

        if(listRecipe==null  || listRecipe.size()==0){

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, EmptyFragment.newInstance(getResources().getString(R.string.empty_fav)))
                    .commit();

        }else{

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, RecipeFragment.newInstance((listRecipe)))
                    .commit();

        }


    }

    private void setUpactionBar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }

}
