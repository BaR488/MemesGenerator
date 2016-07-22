package com.example.boris.memesgenerator.Helpers.SQLHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.boris.memesgenerator.DAOs.MemesDAO;
import com.example.boris.memesgenerator.Entities.Meme;
import com.example.boris.memesgenerator.Helpers.AssetsHelper.MemesAssetsHelper;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by boris on 15.07.2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 13;
    private MemesDAO memesDAO = null;
    private static final String DATABASE_NAME = "memesGenerator.ormdb";
    private Context context;
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try
        {
            TableUtils.createTable(connectionSource, Meme.class);
            for (String file : MemesAssetsHelper.getLocalMemes(context)) {
                try {
                    Meme meme = new Meme();
                    meme.setResource(file);
                    meme.setTags(MemesAssetsHelper.getTagsByPath(context, file));
                    HelperFactory.getHelper().getMemesDAO().create(meme);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (java.sql.SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            //Так делают ленивые, гораздо предпочтительнее не удаляя БД аккуратно вносить изменения
            TableUtils.dropTable(connectionSource, Meme.class, true);
            onCreate(database, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVersion);
            throw new RuntimeException(e);
        }
    }

    public MemesDAO getMemesDAO() throws SQLException{
        if(memesDAO == null){
            memesDAO = new MemesDAO(getConnectionSource(), Meme.class);
        }
        return memesDAO;
    }

    //выполняется при закрытии приложения
    @Override
    public void close(){
        super.close();
        memesDAO = null;
    }
}