package com.tpg.movierx.service;

import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbSearchMovies;
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

    @Inject
    public MovieService(OmdbApi api) {
        this.api = api;
    }

    public Observable<OmdbSearchMovies> searchMovie(String title) {
        return api.searchByTitle(title).compose(RxLog.logObservable())
                .retry(3)
                .onErrorReturn(OmdbSearchMovies::new)
                .subscribeOn(Schedulers.io());
    }
}
