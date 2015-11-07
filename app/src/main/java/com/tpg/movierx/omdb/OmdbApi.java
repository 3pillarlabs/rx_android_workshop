package com.tpg.movierx.omdb;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by karoly.szanto on 24/10/15.
 */
public interface OmdbApi {

    @GET("/?plot=full&tomatoes=true")
    Observable<OmdbMovie> getByTitle(@Query(value = "t", encoded = true) String title);

    @GET("/")
    Observable<OmdbSearchMovies> searchByTitle(@Query(value = "s", encoded = true) String title);
}
