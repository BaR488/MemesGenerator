package com.example.boris.memesgenerator.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import com.example.boris.memesgenerator.Adapters.MemeImageAdapter;
import com.example.boris.memesgenerator.R;

/**
 * Created by boris on 14.07.2016.
 */
public class ChooseLayoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_layout, container, false);

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new MemeImageAdapter(this.getActivity()));

        EditText editText = (EditText) view.findViewById(R.id.findMeme);

        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        // Inflate the layout for this fragment
        return view;
    }
}
