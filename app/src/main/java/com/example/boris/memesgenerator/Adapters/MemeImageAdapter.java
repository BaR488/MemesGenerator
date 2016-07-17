package com.example.boris.memesgenerator.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.boris.memesgenerator.Entities.Meme;
import com.example.boris.memesgenerator.Helpers.AssetsHelper.MemesAssetsHelper;

import java.util.ArrayList;

/**
 * Created by boris on 14.07.2016.
 */
public class MemeImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Meme>  memes = new ArrayList<>();

    public MemeImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return memes.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageDrawable(MemesAssetsHelper.getDrawable(mContext, memes.get(position)));
        return imageView;
    }

    public void updateData(ArrayList<Meme> memeArrayList) {
        memes = memeArrayList;
        notifyDataSetChanged();
    }
}
