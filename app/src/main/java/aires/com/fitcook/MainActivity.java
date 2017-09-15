package aires.com.fitcook;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import aires.com.fitcook.dao.RecipeDAO;
import aires.com.fitcook.dao.RecipeFirebase;
import aires.com.fitcook.entity.Category;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.fragment.EmptyFragment;
import aires.com.fitcook.fragment.LoadingFragment;
import aires.com.fitcook.fragment.RecipeFragment;
import aires.com.fitcook.fragment.SearchDialogFragment;
import aires.com.fitcook.util.AnalyticsUtil;
import aires.com.fitcook.util.Migrar;
import aires.com.fitcook.util.SyncUtils;

public class MainActivity extends AppCompatActivity {

    public static final String FRAGMENT = "FRAGMENT";

    private DrawerLayout mDrawerLayout;
    private SyncUtils.CallBack callBack;

    private RecipeDAO recipeDAO;
    private RecipeFirebase recipe2DAO;

    private List<Recipe> listRecipe;
    private List<Recipe> searchRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new Migrar().doMigrar(this);

        AnalyticsUtil.send(this, "MainActivity");
        searchRecipe=new ArrayList<>();

        recipe2DAO= new RecipeFirebase();
        recipeDAO = new RecipeDAO(this);
        setUpActionBar();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
            setupDrawerContent(navigationView);


        readAll();
    }

    private void readAll(){


        recipe2DAO.read(new RecipeFirebase.CallBack() {
            @Override
            public void onResponse(Object obj) {

                listRecipe= (List<Recipe>) obj;
                handleRecipes(listRecipe);
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
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

    }

    private void setupDrawerContent(final NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        if (menuItem.isChecked()) {
                            mDrawerLayout.closeDrawers();
                            return true;
                        }

                        switch (menuItem.getItemId()) {

                            case R.id.nav_home:
                                readAll();
                                break;
                            case R.id.fav:
                                handleFavorite();
                                break;
                            case R.id.share_main:
                                handleShare();
                                break;


                        }

                        mDrawerLayout.closeDrawers();

                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.drawer_view);
                        navigationView.getMenu().findItem(menuItem.getItemId()).setChecked(true);

                        setTitle(menuItem.getTitle());

                        return true;

                    }
                });

        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private  void handleRecipes(List<Recipe> listRecipe){

        setTitle(getResources().getString(R.string.all_recipes));

        if(listRecipe==null  || listRecipe.size()==0){

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, EmptyFragment.newInstance(R.drawable.icone,getResources().getString(R.string.empty_recipes)))
                    .commitAllowingStateLoss();

        }else{

            showRecipeFragment(listRecipe);

        }

    }

    private  void loadingFragment(){

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, LoadingFragment.newInstance())
                    .commitAllowingStateLoss();



    }


    public void handleShare(){

        String text="Experimente esse app:https://play.google.com/store/apps/details?id=aires.com.fitcook";

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Receitas");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(sharingIntent, "Compartilhar"));

    }

    public void handleFavorite(){


        final List<Recipe> listRecipe =recipeDAO.getAll();


        if(listRecipe==null  || listRecipe.size()==0){

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, EmptyFragment.newInstance(R.drawable.icone,getResources().getString(R.string.empty_fav)))
                    .commit();

        }else{

            showRecipeFragment(listRecipe);

        }

    }


    private void showRecipeFragment(List<Recipe> listRecipe){

        getSupportFragmentManager().beginTransaction().replace(R.id.container, RecipeFragment.newInstance((listRecipe)),FRAGMENT)
                    .commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);



        // Associate searchable configuration with the SearchView
        SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if(listRecipe==null)return false;

                searchRecipe.clear();

                for(Recipe r: listRecipe){

                    if(r.getName().toLowerCase().contains(s.toLowerCase())){
                        searchRecipe.add(r);
                    }

                }

                handleRecipes(searchRecipe);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                handleRecipes(listRecipe);
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


}
