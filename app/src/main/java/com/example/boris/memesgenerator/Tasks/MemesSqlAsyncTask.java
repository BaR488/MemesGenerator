package com.example.boris.memesgenerator.Tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.boris.memesgenerator.Adapters.MemeImageAdapter;
import com.example.boris.memesgenerator.Entities.Meme;
import com.example.boris.memesgenerator.Helpers.SQLHelpers.HelperFactory;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by boris on 17.07.2016.
 */
public class MemesSqlAsyncTask extends AsyncTask <String, Void,ArrayList<Meme>> {

    ProgressBar progressBar;

    MemeImageAdapter memeImageAdapter;

    public MemesSqlAsyncTask(ProgressBar progressBar, MemeImageAdapter memeImageAdapter) {
        this.progressBar = progressBar;
        this.memeImageAdapter = memeImageAdapter;
    }

    @Override
    protected ArrayList<Meme> doInBackground(String... params) {
        ArrayList<Meme> memes = new ArrayList<>();
        try {
//            TimeUnit.SECONDS.sleep(2);
            memes = (ArrayList<Meme>) HelperFactory.getHelper().getMemesDAO().getAllMemesByTag(params[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memes;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(ArrayList<Meme> result) {
        memeImageAdapter.updateData(result);
        progressBar.setVisibility(View.GONE);
    }
}
