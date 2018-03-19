package aires.com.fitcook;


import android.content.Intent;


import android.os.Build;
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

import com.google.firebase.database.DatabaseError;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;


import aires.com.fitcook.dao.RecipeDAO;
import aires.com.fitcook.dao.RecipeFirebase;
import aires.com.fitcook.entity.Recipe;
import aires.com.fitcook.util.AnalyticsUtil;
import aires.com.fitcook.util.JsonUtil;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "EXTRA_RECIPE_ID";
    RecipeFirebase recipeFirebase;
    RecipeDAO recipeDAO;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        AnalyticsUtil.send(this, "RecipeDetailsActivity");

        recipeFirebase = new RecipeFirebase();
        recipeDAO = new RecipeDAO(this);
        Intent intent = getIntent();
        final String id = intent.getStringExtra(EXTRA_RECIPE_ID);

        recipeFirebase.read(id, new RecipeFirebase.CallBack() {
            @Override
            public void onResponse(Object obj) {

                recipe= (Recipe) obj;

                Recipe r= recipeDAO.read(recipe.getPublicId());

                recipe.setFavorite(!(r==null));

                CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                collapsingToolbar.setTitle(recipe.getName());
                collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);

                loadBackdrop();

                init();

            }

            @Override
            public void onErrorResponse(DatabaseError databaseError) {

            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }



    private void loadBackdrop() {

        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);

        PicassoCache.getPicassoInstance(this).load(recipe.getUrl()).into(imageView);

        final FloatingActionButton fab =(FloatingActionButton)findViewById(R.id.fab);

        if(recipe.getFavorite()){
            fab.setImageResource(R.drawable.ic_favorite_white_48dp);
        }else{
            fab.setImageResource(R.drawable.ic_favorite_border_white_48dp);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recipe.getFavorite()) {

                    recipeDAO.delete(recipe.getPublicId());
                    fab.setImageResource(R.drawable.ic_favorite_border_white_48dp);
                } else {

                    recipeDAO.merge(recipe);
                    fab.setImageResource(R.drawable.ic_favorite_white_48dp);

                }


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
                checkBox.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Medium);
                checkBox.setPadding(8,8,8,8);
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

                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setPadding(8,8,8,8);
                LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

                linearLayout.setLayoutParams(LLParams);

                TextView ind =new TextView(this);
                ind.setText( index+"");
                ind.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Large);
                ind.setPadding(4, 4, 4, 4);
                ind.setTextColor(getResources().getColor(R.color.colorAccent));

                TextView inst =new TextView(this);
                inst.setText( instruction);
                inst.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Medium);
                inst.setPadding(4, 4, 4, 4);

                linearLayout.addView(ind);
                linearLayout.addView(inst);

                containerInstructions.addView(linearLayout);
                index++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void share(){

        try{

            StringBuilder shared= new StringBuilder("");

            shared.append(recipe.getName()).append("\n\n");

            shared.append(recipe.getDescription()).append("\n\n");

            List< String > ingredientsList = JsonUtil.parseList(new JSONArray(recipe.getIngredients()));
            shared.append("Ingredientes").append("\n\n");
            for(String ingredients: ingredientsList){
                shared.append(ingredients).append("\n");
            }
            shared.append("\n");
            List< String > instructionList = JsonUtil.parseList(new JSONArray(recipe.getInstruction()));
            shared.append("Preparo").append("\n\n");
            int index=1;
            for(String instruction: instructionList){
                shared.append(index).append("-").append(instruction).append("\n");
                index++;
            }


            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shared.toString());
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

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
        
        if (id == R.id.action_share) {
            share();
            return true;
        }else if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportFinishAfterTransition();
        }else{
            super.onBackPressed();
        }

    }
}
