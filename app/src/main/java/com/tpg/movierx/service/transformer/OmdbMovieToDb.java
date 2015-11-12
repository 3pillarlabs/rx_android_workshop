package com.tpg.movierx.service.transformer;

import android.content.ContentValues;

import com.tpg.movierx.db.MovieItem;
import com.tpg.movierx.omdb.OmdbMovieDetails;

/**
 * Created by ciprian.grigor on 12/11/15.
 */
public class OmdbMovieToDb {

    public static ContentValues buildMovieItemDb(OmdbMovieDetails omdbMovie) {
        return new MovieItem.Builder().listId(1)
                .actors(omdbMovie.actors)
                .plot(omdbMovie.plot)
                .rating(omdbMovie.imdbRating)
                .poster(omdbMovie.posterUri.toString())
                .title(omdbMovie.title)
                .build();
    }
}
