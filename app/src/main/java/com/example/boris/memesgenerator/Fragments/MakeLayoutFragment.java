package com.example.boris.memesgenerator.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.boris.memesgenerator.Helpers.BitmapHelper;
import com.example.boris.memesgenerator.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by boris on 14.07.2016.
 */
public class MakeLayoutFragment extends Fragment {

    IFindListener findListener;
    public Drawable drawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_layout, container, false);
        setupListeners(view);

        EditText top = (EditText) view.findViewById(R.id.topText);
        EditText bottom = (EditText) view.findViewById(R.id.bottomText);

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

    @Override
    public void onResume() {
        super.onResume();
        if (drawable != null){
            ImageView imageView = (ImageView) getView().findViewById(R.id.meme);
            imageView.setImageDrawable(drawable);
        }else{
            RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.mem4ik);
            relativeLayout.setVisibility(View.GONE);

            SeekBar seekBar = (SeekBar) getView().findViewById(R.id.seekSize);
            seekBar.setVisibility(View.GONE);
            SeekBar seekBar1 = (SeekBar) getView().findViewById(R.id.seekMargen);
            seekBar1.setVisibility(View.GONE);
            Button button  = (Button) getView().findViewById(R.id.shareButton);
            button.setEnabled(false);

        }
    }

    public void setupListeners(final View view){
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

        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekSize);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                EditText editTextTop = (EditText) view.findViewById(R.id.topText);
                EditText editTextBot = (EditText) view.findViewById(R.id.bottomText);
                editTextTop.setTextSize(TypedValue.COMPLEX_UNIT_PT, progress);
                editTextBot.setTextSize(TypedValue.COMPLEX_UNIT_PT, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar seekBar1 = (SeekBar) view.findViewById(R.id.seekMargen);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Resources r = getActivity().getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        progress,
                        r.getDisplayMetrics()
                );

                EditText editTextTop = (EditText) view.findViewById(R.id.topText);
                EditText editTextBot = (EditText) view.findViewById(R.id.bottomText);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editTextTop.getLayoutParams();
                layoutParams.setMargins(
                        layoutParams.leftMargin,
                        px,
                        layoutParams.rightMargin,
                        layoutParams.bottomMargin);
                editTextTop.setLayoutParams(layoutParams);

                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) editTextBot.getLayoutParams();
                layoutParams1.setMargins(
                        layoutParams1.leftMargin,
                        layoutParams1.topMargin,
                        layoutParams.rightMargin,
                        px);
                editTextBot.setLayoutParams(layoutParams1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
                    Environment.DIRECTORY_PICTURES).toString() + "/" + nd + ".png";

            // create bitmap screen capture

            View v1 = getView().findViewById(R.id.mem4ik);
            v1.requestFocus();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = BitmapHelper.TrimBitmap(Bitmap.createBitmap(v1.getDrawingCache()));
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            imageSaved = bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream);
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
