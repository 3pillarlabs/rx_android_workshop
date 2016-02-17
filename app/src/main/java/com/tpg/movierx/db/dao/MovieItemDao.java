package com.tpg.movierx.db.dao;

import android.content.ContentValues;

import com.squareup.sqlbrite.BriteDatabase;
import com.tpg.movierx.db.Util;
import com.tpg.movierx.db.model.MovieItem;

import java.util.List;

import rx.Observable;

/**
 * Created by karoly.szanto on 17/02/16.
 */
public class MovieItemDao {

    private final BriteDatabase db;

    public MovieItemDao(final BriteDatabase db) {
        this.db = db;
    }

    public Observable<List<MovieItem>> allMovieItems() {
        return db.createQuery(MovieItem.TABLE, Util.ALL_MOVIES_QUERY).mapToList(MovieItem.MAPPER);
    }

    public long insert(final String title, final String actors, final String plot, final String imdRating, final String poster) {
        final ContentValues movieItem = new MovieItem.Builder()
                .title(title)
                .actors(actors)
                .plot(plot)
                .rating(imdRating)
                .poster(poster)
                .build();

        return db.insert(MovieItem.TABLE, movieItem);
    }
}
