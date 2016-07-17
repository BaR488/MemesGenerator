package com.example.boris.memesgenerator.DAOs;

import com.example.boris.memesgenerator.Entities.Meme;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by boris on 15.07.2016.
 */
public class MemesDAO extends BaseDaoImpl<Meme,Integer>{

    public MemesDAO(ConnectionSource connectionSource, Class<Meme> memeClass) throws SQLException {
        super(connectionSource,memeClass);
    }

    public List<Meme> getAllMemesByTag(String tag) throws SQLException{
        if(tag !=null && !tag.isEmpty()){
            QueryBuilder<Meme, Integer> queryBuilder = queryBuilder();
            queryBuilder.where().like("tags", "%"+tag+"%");
            PreparedQuery<Meme> preparedQuery = queryBuilder.prepare();
            List<Meme> memeList =query(preparedQuery);
            return memeList;
        }else {
            return getAllMemes();
        }

    }

    public List<Meme> getAllMemes() throws SQLException{
        return this.queryForAll();
    }
}
