package com.example.boris.memesgenerator.Helpers.AssetsHelper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import com.example.boris.memesgenerator.Entities.Meme;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * Created by boris on 16.07.2016.
 */
public class MemesAssetsHelper {
    private static String path = "memes";
    private static String tagsPath = "tags";

    public static String[] getLocalMemes(Context context) throws IOException {
        AssetManager assets = context.getAssets();
        String [] strings = assets.list(path);
        for (String s : strings) {
            s = path +"/"+ s;
        }
        return strings;
    }

    public static String getTagsByPath(Context context, String path){
        try{
            AssetManager assets = context.getAssets();
            InputStream is = assets.open(tagsPath+"/"+path.replace(".jpg",".txt"));
            String s = convertStreamToString(is);
            is.close();
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static String convertStreamToString(InputStream inputStream) {
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, Charset.forName("UTF-8"));
            String theString = writer.toString();
            return  theString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Drawable getDrawable(Context context, Meme meme) {
        try {
            // получаем входной поток
            InputStream ims = context.getAssets().open(path +"/"+meme.getResource());
            // загружаем как Drawable
            return Drawable.createFromStream(ims, null);

        }
        catch(IOException ex) {
            return null;
        }
    }

}
