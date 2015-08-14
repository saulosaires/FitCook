package aires.com.fitcook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import aires.com.fitcook.dao.CategoryDAO;
import aires.com.fitcook.dao.IngredientsDAO;
import aires.com.fitcook.dao.InstructionDAO;
import aires.com.fitcook.dao.RecipeDAO;
import aires.com.fitcook.entity.Category;
import aires.com.fitcook.entity.Ingredients;
import aires.com.fitcook.entity.Instruction;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.fragment.RecipeFragment;
import aires.com.fitcook.util.JsonUtil;
import aires.com.fitcook.webservice.WebService;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        boolean firstAccess= FitCookApp.getSharedPreferencesValue(this,FitCookApp.FIRST_ACCESS,true);

        if(firstAccess){

            new RetrieveAllRecipes().execute();
        }else{

            new UpdateRecipes().execute();
        }


    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch(menuItem.getItemId()){

                            case R.id.fav: startActivity(new Intent(getApplicationContext(),FavActivity.class)); break;

                        }


                        /*
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                         */
                        return true;

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
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


    public class RetrieveAllRecipes extends AsyncTask<Void,Void,List<Recipe>>{

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd =ProgressDialog.show(MainActivity.this,getResources().getString(R.string.recipes_retrieve_title),getResources().getString(R.string.recipes_retrieve_msg));
            pd.show();
        }

        @Override
        protected List<Recipe> doInBackground(Void... params) {



            WebService.CallBack callBack =new WebService.CallBack() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String status = response.getString("status");

                        if(status.equals("success")){

                            FitCookApp.putSharedPreferencesValue(getApplicationContext(),FitCookApp.TIME_RETRIEVE_ALL,response.getLong("time"));

                            JSONArray arrayRecipe = new JSONArray(response.getString("array"));

                            List<Recipe> listRecipe= JsonUtil.parseRecipe(arrayRecipe);

                            if (listRecipe.size() > 0) {

                                RecipeDAO recipeDAO=new RecipeDAO(getApplicationContext());
                                CategoryDAO categoryDAO=new CategoryDAO(getApplicationContext());
                                IngredientsDAO ingredientsDAO=new IngredientsDAO(getApplicationContext());
                                InstructionDAO instructionDAO=new InstructionDAO(getApplicationContext());

                                for(Recipe recipe:listRecipe) {
                                    recipeDAO.create(recipe);


                                    for (Category cat :recipe.getCategories()) {
                                        categoryDAO.create(cat);
                                    }

                                    for (Instruction i :recipe.getInstructionList()) {
                                        instructionDAO.create(i);
                                    }

                                    for (Ingredients i :recipe.getIngredientsList()) {
                                        ingredientsDAO.create(i);
                                    }

                                }


                                FitCookApp app = (FitCookApp) getApplication();

                                app.setRecipeList(listRecipe);

                                FragmentManager fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.container, RecipeFragment.newInstance((app.getRecipeList())))
                                        .commit();

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(pd!=null)pd.dismiss();


                }

                @Override
                public void onErrorResponse(VolleyError error) {

                    if(pd!=null)pd.dismiss();
                }
            };

            FitCookApp.getInstance().cancelAllRequests(WebService.retrieve);
            WebService.retrieveRecipes(callBack);


            return null;
        }
    }

    public class UpdateRecipes extends AsyncTask<Void,Void,List<Recipe>>{

        @Override
        protected List<Recipe> doInBackground(Void... params) {



            WebService.CallBack callBack =new WebService.CallBack() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String status = response.getString("status");

                        if(status.equals("success")){

                            JSONArray arrayRecipe = new JSONArray(response.getString("array"));

                            List<Recipe> listRecipe= new ArrayList<Recipe>();

                            for(int i=0;i<arrayRecipe.length();i++){

                                listRecipe.add(new Gson().fromJson(arrayRecipe.getJSONObject(i).toString(), Recipe.class) );
                            }

                            if (listRecipe.size() > 0) {

                                FitCookApp app = (FitCookApp) getApplication();

                                app.setRecipeList(listRecipe);

                                FragmentManager fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.container, RecipeFragment.newInstance((app.getRecipeList())))
                                        .commit();

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onErrorResponse(VolleyError error) {


                }
            };

            FitCookApp.getInstance().cancelAllRequests(WebService.retrieve);
            WebService.retrieveRecipes(callBack);


            return null;
        }
    }

}
