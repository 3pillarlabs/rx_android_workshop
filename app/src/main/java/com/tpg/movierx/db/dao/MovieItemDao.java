package com.tpg.movierx.db.dao;

import android.content.ContentValues;

import com.squareup.sqlbrite.BriteDatabase;
import com.tpg.movierx.db.Util;
import com.tpg.movierx.db.model.MovieItem;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by karoly.szanto on 17/02/16.
 */
public class MovieItemDao {

    private final BriteDatabase db;

    public MovieItemDao(final BriteDatabase db) {
        this.db = db;
    }

    public Observable<List<MovieItem>> allMovieItems() {
        return db.createQuery(MovieItem.TABLE, Util.ALL_MOVIES_QUERY)
                .mapToList(MovieItem.MAPPER)
                .subscribeOn(Schedulers.io());
    }

    public long insert(final ContentValues movieItem) {
        return db.insert(MovieItem.TABLE, movieItem);
    }
}
