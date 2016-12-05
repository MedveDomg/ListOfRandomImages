package omg.medvedomg.listofrandomimages1.ui;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import omg.medvedomg.listofrandomimages1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogRandomImageFragment extends DialogFragment {

    private String URL;
    private ImageView randomImageView;


    public DialogRandomImageFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_random_image, container);
        randomImageView = (ImageView) view.findViewById(R.id.randomImageView);
        String url = getArguments().getString("url_image");
        Picasso.with(getActivity())
                .load(url)
                .into(randomImageView);
        return view;
    }

}
