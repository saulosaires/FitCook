package aires.com.fitcook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import aires.com.fitcook.R;

public class EmptyFragment extends Fragment {

    private static final String ARG_ID_IMG = "ARG_ID_IMG";
    private static final String ARG_MSG = "ARG_MSG";

    private ImageView img;
    private TextView msgEmpty;
    private String msg;
    private Integer idImg;

    public static EmptyFragment newInstance(Integer idImg,String msg) {
        EmptyFragment fragment = new EmptyFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_ID_IMG, idImg);
        args.putString(ARG_MSG, msg);

        fragment.setArguments(args);
        return fragment;
    }

    public EmptyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            msg   = getArguments().getString(ARG_MSG);
            idImg = getArguments().getInt(ARG_ID_IMG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_empty, container, false);

        img      = (ImageView)view.findViewById(R.id.logoEmpty);
        msgEmpty = (TextView)view.findViewById(R.id.msgEmpty);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(msgEmpty!=null && msg!=null)
            msgEmpty.setText(msg);


        if(img!=null && idImg!=null)
            img.setImageResource(idImg);


    }
}
