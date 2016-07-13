package aires.com.fitcook.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import aires.com.fitcook.R;

/**
 * Created by saulo on 07/07/2016.
 */
public class SearchDialogFragment extends DialogFragment {

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

        v.setMinimumWidth((int) displayRectangle.width());
        v.setMinimumHeight((int) displayRectangle.height());

        return v;
    }
}
