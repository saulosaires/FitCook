package aires.com.fitcook;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import java.util.List;

import aires.com.fitcook.dao.RecipeDAO;
import aires.com.fitcook.entity.Category;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.fragment.EmptyFragment;
import aires.com.fitcook.fragment.LoadingFragment;
import aires.com.fitcook.fragment.RecipeFragment;
import aires.com.fitcook.fragment.SearchDialogFragment;
import aires.com.fitcook.util.AnalyticsUtil;
import aires.com.fitcook.util.SyncUtils;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT = "FRAGMENT";

    private static boolean  syncked=false;

    private DrawerLayout mDrawerLayout;
    private SyncUtils.CallBack callBack;

    private RecipeDAO recipeDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnalyticsUtil.send(this, "MainActivity");

        setUpActionBar();
        recipeDAO=new RecipeDAO(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
            setupDrawerContent(navigationView);


        if(!syncked) {
            sync();
        }else{
            handleRecipes(recipeDAO.getAll());
        }


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }



    private  void sync(){

        callBack=new SyncUtils.CallBack() {
            @Override
            public void onStart() {

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, LoadingFragment.newInstance())
                        .commit();

            }

            @Override
            public void onFinished(boolean error) {

                handleRecipes(recipeDAO.getAll());
                syncked=true;
            }
        };

        SyncUtils.TriggerRefresh(this, callBack);

    }

    public void setUpActionBar(){

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

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
                                handleRecipes(recipeDAO.getAll());
                                break;
                            case R.id.fav:
                                handleFavorite();
                                break;
                            case R.id.share_main:
                                handleShare();
                                break;

                            default:
                                handleCategory(menuItem.getItemId());
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

    public void handleShare(){

        String text="Experimente esse app:https://play.google.com/store/apps/details?id=aires.com.fitcook";

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Receitas");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(sharingIntent, "Compartilhar"));

    }

    public void handleFavorite(){


        List<Recipe> listRecipe = recipeDAO.readFav();

        if(listRecipe==null  || listRecipe.size()==0){

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, EmptyFragment.newInstance(R.drawable.icone,getResources().getString(R.string.empty_fav)))
                    .commit();

        }else{

            showRecipeFragment(listRecipe);

        }

    }

    public void handleCategory(int categoryId){

        Category cat =FitCookApp.mapCategory.get(categoryId);

        List<Recipe> listRecipe = recipeDAO.readByCategory(cat);

        if(listRecipe==null  || listRecipe.size()==0){

            String empty= getResources().getString(R.string.empty_category);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, EmptyFragment.newInstance(cat.getIcon(),empty))
                    .commit();

        }else{

            showRecipeFragment(listRecipe);

        }

    }

    private void showRecipeFragment(List<Recipe> listRecipe){

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeFragment recipeFragment = (RecipeFragment) fragmentManager.findFragmentByTag(FRAGMENT);

        if(recipeFragment!=null){
            recipeFragment.init(listRecipe);
            recipeFragment.update();
            fragmentManager.beginTransaction().show(recipeFragment).commit();
        }else{
            fragmentManager.beginTransaction().replace(R.id.container, RecipeFragment.newInstance((listRecipe)),FRAGMENT)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.search:{
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                SearchDialogFragment groupDialogFragment = SearchDialogFragment.newInstance();
                groupDialogFragment.show(ft,"dialog");
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
