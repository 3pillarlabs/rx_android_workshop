package com.tpg.movierx.omdb;

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
