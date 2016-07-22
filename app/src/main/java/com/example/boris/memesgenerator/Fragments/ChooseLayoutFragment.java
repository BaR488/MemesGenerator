package com.example.boris.memesgenerator.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.boris.memesgenerator.Adapters.MemeImageAdapter;
import com.example.boris.memesgenerator.Entities.Meme;
import com.example.boris.memesgenerator.R;
import com.example.boris.memesgenerator.Tasks.MemesSqlAsyncTask;

/**
 * Created by boris on 14.07.2016.
 */
public class ChooseLayoutFragment extends Fragment {

    IChooseMeme iChooseMeme;
    Context context;

    public interface IChooseMeme {
        public void onChooseMeme(Meme drawable);
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

        final MemeImageAdapter memeImageAdapter = new MemeImageAdapter(this.getActivity());

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        final EditText editText = (EditText) view.findViewById(R.id.findMeme);

        final MemesSqlAsyncTask memesSqlAsyncTask = new MemesSqlAsyncTask(progressBar,memeImageAdapter);
        memesSqlAsyncTask.execute(editText.getText().toString());

        GridView gridview = (GridView) view.findViewById(R.id.gridview);

        gridview.setAdapter(memeImageAdapter);

        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MemesSqlAsyncTask memesSqlAsyncTask = new MemesSqlAsyncTask(progressBar,memeImageAdapter);
                memesSqlAsyncTask.execute(s.toString());
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ImageView imageView = (ImageView) v;
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                Meme meme = (Meme) memeImageAdapter.getItem(position);
                iChooseMeme.onChooseMeme(meme);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
