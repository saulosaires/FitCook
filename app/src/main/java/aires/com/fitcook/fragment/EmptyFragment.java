package aires.com.fitcook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import aires.com.fitcook.R;

public class EmptyFragment extends Fragment {

    private static final String ARG_MSG = "ARG_MSG";
    private TextView msgEmpty;
    private String msg;

    public static EmptyFragment newInstance(String msg) {
        EmptyFragment fragment = new EmptyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MSG, msg);

        fragment.setArguments(args);
        return fragment;
    }

    public EmptyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msg = getArguments().getString(ARG_MSG);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_empty, container, false);

        msgEmpty =(TextView)view.findViewById(R.id.msgEmpty);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(msgEmpty!=null && msg!=null)
            msgEmpty.setText(msg);
    }
}
