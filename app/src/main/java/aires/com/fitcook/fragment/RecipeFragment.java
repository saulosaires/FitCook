package aires.com.fitcook.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.balysv.materialripple.MaterialRippleLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import aires.com.fitcook.R;
import aires.com.fitcook.RecipeDetailsActivity;
import aires.com.fitcook.entity.Recipe;

public class RecipeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GridLayoutManager manager=null;

    private List<Recipe> listRecipes;

    public static RecipeFragment newInstance(List<Recipe> listRecipes) {

        RecipeFragment fragment = new RecipeFragment();
        fragment.init(listRecipes);

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    public RecipeFragment() {}

    public void init(List<Recipe> listRecipes) {
        this.listRecipes=listRecipes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_recipe, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            manager = new GridLayoutManager(getActivity(), 2);
        }else if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            manager = new GridLayoutManager(getActivity(), 4);
        }

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(new RecipeAdapter(listRecipes));

    }



    public void update(){
        mRecyclerView.setAdapter(new RecipeAdapter(listRecipes));

    }

    public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

        private List<Recipe> listRecipe;

        public RecipeAdapter(List<Recipe> listRecipe) {
            super();
            this.listRecipe=listRecipe;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catalog_card, viewGroup, false);

            return new ViewHolder(v,viewGroup.getContext());
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, int i) {

            final Recipe recipe   = listRecipe.get(i);

            viewHolder.title.setText(recipe.getName());
            Picasso.with(viewHolder.context)
                    .load(recipe.getUrlSmall())
                    .into(viewHolder.cover, new Callback() {
                        @Override
                        public void onSuccess() {

                            Bitmap bitmap =  ((BitmapDrawable)viewHolder.cover.getDrawable()).getBitmap();

                            Palette palette  = Palette.from(bitmap).generate();
                            Palette.Swatch swatch = palette.getVibrantSwatch();

                            if (swatch != null) {
                                viewHolder.warpper.setBackgroundColor(swatch.getRgb());

                                //viewHolder.title.setBackgroundColor(swatch.getRgb());
                                viewHolder.title.setTextColor(swatch.getTitleTextColor());


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

            Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);

            intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_ID,publicId);

            Pair<View, String> p1 = Pair.create((View)viewHolder.cover, "profile");
            Pair<View, String> p2 = Pair.create((View)viewHolder.title, "name_recipe");

            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(getActivity(), p1,p2);

            getActivity().startActivity(intent,options.toBundle());

        }

        @Override
        public int getItemCount() {
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



}
