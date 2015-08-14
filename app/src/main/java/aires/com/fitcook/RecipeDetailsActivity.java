package aires.com.fitcook;

import android.content.Intent;

import android.support.design.widget.CollapsingToolbarLayout;
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

import aires.com.fitcook.entity.Ingredients;
import aires.com.fitcook.entity.Instruction;
import aires.com.fitcook.entity.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_INDEX = "EXTRA_RECIPE_INDEX";

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        Intent intent = getIntent();
        final int id = intent.getIntExtra(EXTRA_RECIPE_INDEX, -1);

        FitCookApp app = (FitCookApp) getApplication();

        recipe= app.getRecipeList().get(id);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(recipe.getName());

        loadBackdrop();

        init();

    }


    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(recipe.getFile().getUrl()).into(imageView);
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

        LinearLayout containerIngredients =(LinearLayout)findViewById(R.id.containerIngredients);

        for(Ingredients ingredients: recipe.getIngredientsList()){

            CheckBox checkBox =new CheckBox(this);
            checkBox.setText(ingredients.getDescription());

            containerIngredients.addView(checkBox);

        }

    }

    public void initInstructions(){


        LinearLayout containerInstructions =(LinearLayout)findViewById(R.id.containerInstructions);

        for(Instruction instruction: recipe.getInstructionList()){

            TextView textView =new TextView(this);
            textView.setText(instruction.getBasis()+"-"+instruction.getDescription());
            textView.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Small);
            textView.setPadding(4,4,4,4);

            containerInstructions.addView(textView);

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
