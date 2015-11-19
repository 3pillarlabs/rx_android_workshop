package com.tpg.movierx.service;

import com.squareup.sqlbrite.BriteDatabase;
import com.tpg.movierx.db.MovieItem;
import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbMovie;
import com.tpg.movierx.omdb.OmdbSearchMovies;
import com.tpg.movierx.service.transformer.OmdbMovieToDb;
import com.tpg.movierx.util.RxLog;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ciprian.grigor on 11/11/15.
 */
@Singleton
public class MovieService {

    private OmdbApi api;
    private BriteDatabase db;

    @Inject
    public MovieService(OmdbApi api, BriteDatabase db) {
        this.api = api;
        this.db = db;
    }

    public Observable<OmdbSearchMovies> searchMovie(String title) {
        return api.searchByTitle(title).compose(RxLog.insertLog())
                .retry(3)
                .onErrorReturn(OmdbSearchMovies::new)
                .subscribeOn(Schedulers.io());
    }

    public Observable<Long> saveMovie(OmdbMovie item) {
        return api.getByTitle(item.title)
                .map(OmdbMovieToDb::buildMovieItemDb)
                .map(cv -> db.insert(MovieItem.TABLE, cv))
                .subscribeOn(Schedulers.io());
    }
}
