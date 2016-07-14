package com.example.boris.memesgenerator.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.boris.memesgenerator.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by boris on 14.07.2016.
 */
public class MakeLayoutFragment extends Fragment {

    IFindListener findListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_layout, container, false);
        setupListeners(view);
        // Inflate the layout for this fragment
        return view;
    }

    public interface IFindListener {
        public void onFind();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            findListener = (IFindListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement IFindListener");
        }
    }

    public void setupListeners(View view){
        Button button = (Button) view.findViewById(R.id.shareButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shareMeme(v);
            }
        });

        EditText editText = (EditText) view.findViewById(R.id.findMeme);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    findListener.onFind();
                }
            }
        });
    }

    public void shareMeme(View view)
    {
        File image = takeScreenshot();
        share(image);
    }

    private File takeScreenshot() {
        Date now = new Date();
        String nd = (String) android.text.format.DateFormat.format("yyyy-MM-dd_hhmmss", now);
        boolean imageSaved = false;
        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES).toString() + "/" + nd + ".jpg";

            // create bitmap screen capture

            View v1 = getView().findViewById(R.id.mem4ik);
            v1.requestFocus();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            imageSaved = bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            if (!imageSaved){
                throw new Exception(getResources().getText(R.string.memeNotSaved).toString());
            }
            return imageFile;
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
            return null;
        }
    }

    private void share(File imageFile) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM,  Uri.fromFile(imageFile));
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
    }
}
