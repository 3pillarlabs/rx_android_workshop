package com.tpg.movierx.omdb;

import java.util.HashMap;
import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by karoly.szanto on 24/10/15.
 */
public class OmdbApiService {

    private static OmdbApi api;

    public static Map<String, String> getFindByTitleOptions(final String title) {
        final Map<String, String> options = new HashMap<>();
        options.put("t", title);

        return options;
    }

    public static OmdbApi getService() {
        if(api == null) {
            api = createApiConnector();
        }

        return api;
    }

    private static OmdbApi createApiConnector() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(OmdbApi.class);
    }

    public interface OmdbApi {

        @GET("/?y=&plot=short&r=json")
        Observable<OmdbMovie> findByTitle(@QueryMap Map<String, String> options);
    }
}
