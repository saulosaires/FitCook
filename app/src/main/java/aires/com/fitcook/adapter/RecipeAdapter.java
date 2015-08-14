package aires.com.fitcook.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import aires.com.fitcook.R;
import aires.com.fitcook.RecipeDetailsActivity;
import aires.com.fitcook.entity.Recipe;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> implements View.OnClickListener {

    private List<Recipe> listRecipe;

    public RecipeAdapter(List<Recipe> listRecipe) {
        super();
        this.listRecipe=listRecipe;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catalog_card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v,viewGroup.getContext());


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Recipe recipe = listRecipe.get(i);
        final int index= i;

        viewHolder.title.setText(recipe.getName());
        Picasso.with(viewHolder.context).load(recipe.getFile().getUrl()).into(viewHolder.cover);

        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Context context = v.getContext();
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_INDEX, index);

                context.startActivity(intent);

            }
        });
        viewHolder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_INDEX, index);

                context.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return listRecipe.size();
    }

    @Override
    public void onClick(View v) {

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView cover;
        public TextView title;

        Context context;

        public ViewHolder(View itemView,Context context) {
            super(itemView);
            this.context=context;
            cover      = (ImageView)itemView.findViewById(R.id.cover);
            title      = (TextView)itemView.findViewById(R.id.title);

        }
    }

}