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
package com.tpg.movierx.db.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.tpg.movierx.db.Db;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

@AutoParcel
public abstract class MovieItem {
    public static final String TABLE = "movie_item";

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String PLOT = "plot";
    public static final String ACTORS = "actors";
    public static final String RATING = "rating";
    public static final String POSTER = "poster";

    public abstract long id();

    public abstract String title();

    public abstract String plot();

    public abstract String actors();

    public abstract String rating();

    public abstract String poster();

    public static final Func1<Cursor, MovieItem> MAPPER = cursor -> {
        final long id = Db.getLong(cursor, ID);
        final String title = Db.getString(cursor, TITLE);
        final String plot = Db.getString(cursor, PLOT);
        final String actors = Db.getString(cursor, ACTORS);
        final String rating = Db.getString(cursor, RATING);
        final String poster = Db.getString(cursor, POSTER);

        return new AutoParcel_MovieItem(id, title, plot, actors, rating, poster);
    };

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(ID, id);
            return this;
        }

        public Builder title(String title) {
            values.put(TITLE, title);
            return this;
        }

        public Builder plot(String plot) {
            values.put(PLOT, plot);
            return this;
        }

        public Builder actors(String actors) {
            values.put(ACTORS, actors);
            return this;
        }

        public Builder rating(String rating) {
            values.put(RATING, rating);
            return this;
        }

        public Builder poster(String poster) {
            values.put(POSTER, poster);
            return this;
        }

        public ContentValues build() {
            return values;
        }

        public MovieItem buildItem() {

            return new AutoParcel_MovieItem(
                    values.getAsLong(ID),
                    values.getAsString(TITLE),
                    values.getAsString(PLOT),
                    values.getAsString(ACTORS),
                    values.getAsString(RATING),
                    values.getAsString(POSTER)
            );
        }
    }
}
