package com.tpg.movierx.omdb;

import retrofit.http.GET;
import retrofit.http.Query;
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
