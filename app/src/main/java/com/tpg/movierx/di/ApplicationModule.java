package com.tpg.movierx.di;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tpg.movierx.BuildConfig;
import com.tpg.movierx.MovieApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide external libs dependencies with application scope
 * <p>
 * Created by ciprian.grigor on 05/10/15.
 */
@Module
public class ApplicationModule {

    private final MovieApplication app;

    private RefWatcher refWatcher = RefWatcher.DISABLED;

    public ApplicationModule(MovieApplication app) {
        this.app = app;
        initRefWatcher();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    RefWatcher provideRefWatcher() {
        if (refWatcher == RefWatcher.DISABLED) {
            initRefWatcher();
        }

        return refWatcher;
    }

    void initRefWatcher() {
        if (BuildConfig.DEBUG) {
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                refWatcher = LeakCanary.install(app);
                Toast.makeText(app, "LeakCanary enabled for Movie App", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(app, "LeakCanary disabled for Movie App", Toast.LENGTH_LONG).show();
            }
        }
    }

    boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(app, permission)
                == PackageManager.PERMISSION_GRANTED;
    }
}
