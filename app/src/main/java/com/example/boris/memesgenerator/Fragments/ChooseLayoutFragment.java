package com.example.boris.memesgenerator.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.boris.memesgenerator.Adapters.MemeImageAdapter;
import com.example.boris.memesgenerator.R;

/**
 * Created by boris on 14.07.2016.
 */
public class ChooseLayoutFragment extends Fragment {

    IChooseMeme iChooseMeme;

    public interface IChooseMeme {
        public void onChooseMeme(Drawable drawable);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            iChooseMeme = (IChooseMeme) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement IFindListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_layout, container, false);

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new MemeImageAdapter(this.getActivity()));

        EditText editText = (EditText) view.findViewById(R.id.findMeme);

        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ImageView imageView = (ImageView) v;
                iChooseMeme.onChooseMeme(imageView.getDrawable());
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
