package aires.com.fitcook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.squareup.picasso.Callback;

import java.util.List;

import aires.com.fitcook.PicassoCache;
import aires.com.fitcook.R;
import aires.com.fitcook.RecipeDetailsActivity;
import aires.com.fitcook.entity.Recipe;

/**
 * Created by Saulo Aires on 10/07/2017.
 */

public class RecipeFullAdapter extends RecyclerView.Adapter<RecipeFullAdapter.ViewHolder> {

    private List<Recipe> listRecipe;
    private Activity activity;

    public RecipeFullAdapter(List<Recipe> listRecipe, Activity activity) {
        super();
        this.listRecipe=listRecipe;
        this.activity=activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catalog_full_card, viewGroup, false);

        return new ViewHolder(v,viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        final Recipe recipe   = listRecipe.get(i);

        viewHolder.title.setText(recipe.getName());
        PicassoCache.getPicassoInstance(viewHolder.context)
                .load(recipe.getUrl())
                .into(viewHolder.cover, new Callback() {
                    @Override
                    public void onSuccess() {

                        Bitmap bitmap =  ((BitmapDrawable)viewHolder.cover.getDrawable()).getBitmap();

                        Palette palette  = Palette.from(bitmap).generate();
                        Palette.Swatch swatch = palette.getVibrantSwatch();

                        if (swatch != null) {
                           // viewHolder.warpper.setBackgroundColor(swatch.getRgb());

                            //viewHolder.title.setBackgroundColor(swatch.getRgb());
                            viewHolder.title.setTextColor(swatch.getRgb());


                        }

                    }

                    @Override
                    public void onError() {

                    }
                });

        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(viewHolder,recipe.getPublicId());
            }
        });

        viewHolder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(viewHolder,recipe.getPublicId());
            }
        });


    }

    private void handleClick(ViewHolder viewHolder, String publicId){

        Intent intent = new Intent(activity, RecipeDetailsActivity.class);

        intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_ID,publicId);

        Pair<View, String> p1 = Pair.create((View)viewHolder.cover, "profile");
        Pair<View, String> p2 = Pair.create((View)viewHolder.title, "name_recipe");

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1,p2);

        activity.startActivity(intent,options.toBundle());

    }

    @Override
    public int getItemCount() {
        
         if(listRecipe==null) return 0;
        return listRecipe.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public MaterialRippleLayout warpper;
        public ImageView cover;
        public TextView title;
        public Context context;

        public ViewHolder(View itemView,Context context) {
            super(itemView);

            this.context= context;
            cover       = (ImageView)itemView.findViewById(R.id.cover);
            title       = (TextView)itemView.findViewById(R.id.title);
            warpper     = (MaterialRippleLayout) itemView.findViewById(R.id.warpper);
        }

    }

}
