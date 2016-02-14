package com.tpg.movierx.omdb;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * The Open Movie Database API
 */
public interface OmdbApi {

    @GET("/?plot=full&tomatoes=true")
    Observable<OmdbMovieDetails> getByTitle(@Query(value = "t", encoded = true) String title);

    @GET("/")
    Observable<OmdbSearchMovies> searchByTitle(@Query(value = "s", encoded = true) String title);
}
