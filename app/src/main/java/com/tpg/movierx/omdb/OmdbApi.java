package com.tpg.movierx.omdb;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by karoly.szanto on 24/10/15.
 */
public interface OmdbApi {

    @GET("/?y=&plot=short&r=json")
    Observable<OmdbMovie> findByTitle(@Query("t") String title);
}
