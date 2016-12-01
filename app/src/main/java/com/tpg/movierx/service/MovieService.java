package com.tpg.movierx.service;

import com.tpg.movierx.db.dao.MovieItemDao;
import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbMovie;
import com.tpg.movierx.omdb.OmdbSearchMovies;
import com.tpg.movierx.service.transformer.OmdbMovieToDb;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Notification;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ciprian.grigor on 16/02/16.
 */
@Singleton
public class MovieService {

    private OmdbApi omdbApi;
    private MovieItemDao dao;

    @Inject
    public MovieService(
            OmdbApi omdbApi,
            MovieItemDao dao) {
        this.omdbApi = omdbApi;
        this.dao = dao;
    }

    public Observable<OmdbSearchMovies> searchMovie(Observable<String> inputObservable) {
        return inputObservable
                .debounce(250, TimeUnit.MILLISECONDS)//avoid too many requests
                .switchMap(this::searchMovie);
    }

    public Observable<OmdbSearchMovies> searchMovie(String title) {
        return omdbApi.searchByTitle(title)
                .retry(2)
                .onErrorReturn(OmdbSearchMovies::new) //never fail
                .subscribeOn(Schedulers.io());
    }

    public Observable<Notification<Long>> saveMovie(OmdbMovie item) {
        return omdbApi.getByTitle(item.title)
                .retry(2)
                .map(OmdbMovieToDb::buildMovieItemDb)
                .map(dao::insert)
                .materialize()
                .subscribeOn(Schedulers.io());
    }
}
