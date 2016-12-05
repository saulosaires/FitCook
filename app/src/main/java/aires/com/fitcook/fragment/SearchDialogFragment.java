package aires.com.fitcook.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import aires.com.fitcook.CategoryActivity;
import aires.com.fitcook.FitCookApp;
import aires.com.fitcook.R;
import aires.com.fitcook.dao.RecipeDAO;
import aires.com.fitcook.entity.Category;
import aires.com.fitcook.entity.Recipe;

public class SearchDialogFragment extends DialogFragment implements View.OnClickListener {

    EditText search;

    public static SearchDialogFragment newInstance() {
        SearchDialogFragment f = new SearchDialogFragment();

        Bundle arg = new Bundle();

        f.setArguments(arg);

        return f;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_DesignMain);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment_dialog, container, false);

        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        v.setMinimumWidth( displayRectangle.width());
        v.setMinimumHeight( displayRectangle.height());

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        search= (EditText) v.findViewById(R.id.search);
        search.requestFocus();

        v.findViewById(R.id.category_drink).setOnClickListener(this);
        v.findViewById(R.id.category_cake).setOnClickListener(this);
        v.findViewById(R.id.category_meat).setOnClickListener(this);
        v.findViewById(R.id.category_snack).setOnClickListener(this);
        v.findViewById(R.id.category_pasta).setOnClickListener(this);
        v.findViewById(R.id.category_salad).setOnClickListener(this);

        fab.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.fab) {

            String txt = search.getText().toString();

            startActivityBySearch(txt);

        } else {

            startActivityByCategory(v.getId());

        }

    }

    private void startActivityBySearch(String txt){

        if(!validSearch(txt))return;

        Intent intent = new Intent(getActivity(), CategoryActivity.class);

        intent.putExtra(CategoryActivity.EXTRA_CATEGORY_SEARCH,txt);

        getDialog().dismiss();
        startActivity(intent);
    }

    private void startActivityByCategory(int id){

        Intent intent = new Intent(getActivity(), CategoryActivity.class);

        intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID,id);

        getDialog().dismiss();
        startActivity(intent);
    }

    private boolean validSearch(String txt){

        if(txt==null || "".equals(txt)){
            Toast.makeText(getActivity(),getResources().getString(R.string.search_empty),Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }


}
