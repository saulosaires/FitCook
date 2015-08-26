package aires.com.fitcook.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.List;

import aires.com.fitcook.R;
import aires.com.fitcook.adapter.RecipeAdapter;
import aires.com.fitcook.entity.Recipe;


public class RecipeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private GridLayoutManager manager;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_recipe, container, false);


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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
 /*
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


}
