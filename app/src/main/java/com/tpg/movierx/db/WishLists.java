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

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

@AutoParcel
public abstract class WishLists {
    public static final String TABLE = "wish_lists";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static Func1<Cursor, List<WishLists>> MAPPER = cursor -> {
        try {
            List<WishLists> values = new ArrayList<>(cursor.getCount());

            while (cursor.moveToNext()) {
                final long id = Db.getLong(cursor, ID);
                final String name = Db.getString(cursor, NAME);
                values.add(new AutoParcel_WishLists(id, name));
            }
            return values;
        } finally {
            cursor.close();
        }
    };

    public abstract long id();

    public abstract String name();

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(ID, id);
            return this;
        }

        public Builder name(String name) {
            values.put(NAME, name);
            return this;
        }

        public ContentValues build() {
            return values; // TODO defensive copy?
        }
    }
}
