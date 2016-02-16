package com.tpg.movierx;

import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbMovie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by ciprian.grigor on 16/02/16.
 */
@Singleton
public class Logic {

    private OmdbApi omdbApi;

    @Inject
    public Logic(OmdbApi omdbApi) {
        this.omdbApi = omdbApi;
    }

    Observable<List<OmdbMovie>> findMoviesObservable(Observable<CharSequence> inputObservable) {
        return inputObservable.map(CharSequence::toString).flatMap(omdbApi::searchByTitle).map(omdbSearchMovies -> omdbSearchMovies.movies);
    }
}
