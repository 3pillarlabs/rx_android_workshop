package com.tpg.movierx.service;

import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbSearchMovies;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ciprian.grigor on 16/02/16.
 */
@Singleton
public class MovieService {

    private OmdbApi omdbApi;

    @Inject
    public MovieService(OmdbApi omdbApi) {
        this.omdbApi = omdbApi;
    }

    public Observable<OmdbSearchMovies> searchMovie(Observable<CharSequence> inputObservable) {
        return inputObservable
                .debounce(250, TimeUnit.MILLISECONDS)//avoid too many requests
                .map(CharSequence::toString)
                .switchMap(this::searchMovie);
    }

    public Observable<OmdbSearchMovies> searchMovie(String title) {
        return omdbApi.searchByTitle(title)
                .retry(2)
                .onErrorReturn(OmdbSearchMovies::new) //never fail
                .subscribeOn(Schedulers.io());
    }

}
