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

import aires.com.fitcook.PicassoCache;
import aires.com.fitcook.R;
import aires.com.fitcook.RecipeDetailsActivity;
import aires.com.fitcook.adapter.RecipeAdapter;
import aires.com.fitcook.adapter.RecipeFullAdapter;
import aires.com.fitcook.entity.Recipe;

public class RecipeFragment extends Fragment {

    private RecyclerView mRecyclerView;


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

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mRecyclerView.setHasFixedSize(false);

        update();

    }



    public void update(){
        mRecyclerView.setAdapter(new RecipeFullAdapter(listRecipes,getActivity()));

    }





}
