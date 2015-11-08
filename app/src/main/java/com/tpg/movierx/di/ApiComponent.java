package com.tpg.movierx.di;

import com.tpg.movierx.omdb.OmdbApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ciprian.grigor on 08/11/15.
 */
@Singleton
@Component(
        modules = ApiModule.class
)
public interface ApiComponent {
    OmdbApi getOmbdApi();
}
