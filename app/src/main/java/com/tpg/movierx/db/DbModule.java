/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tpg.movierx.db;

import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.tpg.movierx.MovieApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DbModule {

    private static final Logger logger = LoggerFactory.getLogger("DbModule");

    private final MovieApplication app;

    public DbModule(final MovieApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    SQLiteOpenHelper provideOpenHelper() {
        return new DbOpenHelper(app);
    }

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return SqlBrite.create(new SqlBrite.Logger() {
            @Override
            public void log(String message) {
                logger.debug("Database {}", message);
            }
        });
    }

    @Provides
    @Singleton
    BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper helper) {
        return sqlBrite.wrapDatabaseHelper(helper);
    }
}
