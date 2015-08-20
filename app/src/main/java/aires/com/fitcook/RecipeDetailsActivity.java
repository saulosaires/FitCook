package aires.com.fitcook;

import android.content.Intent;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import aires.com.fitcook.dao.RecipeDAO;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.util.JsonUtil;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "EXTRA_RECIPE_ID";
    RecipeDAO recipeDAO;

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeDAO= new RecipeDAO(getApplicationContext());

        Intent intent = getIntent();
        final String id = intent.getStringExtra(EXTRA_RECIPE_ID);

        recipe= recipeDAO.read(id);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(recipe.getName());
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);

        loadBackdrop();

        init();


    }


    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(recipe.getUrl()).into(imageView);

        final FloatingActionButton fab =(FloatingActionButton)findViewById(R.id.fab);

        if(recipe.getFavorite()){
            fab.setImageResource(R.drawable.ic_favorite_white_48dp);
        }else{
            fab.setImageResource(R.drawable.ic_favorite_border_white_48dp);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(recipe.getFavorite()){
                    fab.setImageResource(R.drawable.ic_favorite_border_white_48dp);
                }else{
                    fab.setImageResource(R.drawable.ic_favorite_white_48dp);

                }

                recipe.setFavorite(!recipe.getFavorite());

                recipeDAO.update(recipe);

            }
        });

    }

    private void init(){

        initDescription();
        initIngredients();
        initInstructions();
    }

    private void initDescription(){

        View cardIntro =findViewById(R.id.card_intro);

        TextView description=  (TextView)cardIntro.findViewById(R.id.description);
        description.setText(recipe.getDescription());

        TextView time=  (TextView)cardIntro.findViewById(R.id.time);
                time.setText(recipe.getTimeToPrepare());

        TextView servings=  (TextView)cardIntro.findViewById(R.id.servings);
                servings.setText(recipe.getServings().toString());

    }

    private void initIngredients(){

        try {
            List< String > ingredientsList = JsonUtil.parseList(new JSONArray(recipe.getIngredients()));

            LinearLayout containerIngredients =(LinearLayout)findViewById(R.id.containerIngredients);

            for(String ingredients: ingredientsList){

                CheckBox checkBox =new CheckBox(this);
                checkBox.setText(ingredients);

                containerIngredients.addView(checkBox);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void initInstructions(){


        try {

            List< String > instructionList = JsonUtil.parseList(new JSONArray(recipe.getInstruction()));

            LinearLayout containerInstructions =(LinearLayout)findViewById(R.id.containerInstructions);

            int index=1;

            for(String instruction: instructionList){

                TextView textView =new TextView(this);
                textView.setText(index+"-"+instruction);
                textView.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Small);
                textView.setPadding(4,4,4,4);

                containerInstructions.addView(textView);
                index++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_recipe_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
