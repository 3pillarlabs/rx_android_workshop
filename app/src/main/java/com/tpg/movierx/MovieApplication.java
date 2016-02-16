package com.tpg.movierx;

import android.app.Application;
import android.content.Context;

import com.tpg.movierx.di.ApplicationComponent;
import com.tpg.movierx.di.ApplicationModule;
import com.tpg.movierx.di.DaggerApplicationComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Movie RX Application
 * <p>
 * Created by ciprian.grigor on 01/10/15.
 */
public class MovieApplication extends Application {

    private static Logger logger = LoggerFactory.getLogger("MovieApplication");

    private ApplicationComponent component;

    public static ApplicationComponent component(Context context) {
        return ((MovieApplication) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        logger.info("create");
        this.component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        logger.info("lowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        logger.info("trimMemory level {}", level);
    }
}

