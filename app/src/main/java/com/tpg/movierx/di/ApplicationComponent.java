package com.tpg.movierx.di;

import com.tpg.movierx.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Application component that is used to inject application scope dependencies
 * <p>
 * Created by ciprian.grigor on 05/10/15.
 */

@Singleton
@Component(
        modules = {ApplicationModule.class, ApiModule.class}
)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
}
