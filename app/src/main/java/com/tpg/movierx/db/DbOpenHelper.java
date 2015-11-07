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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

final class DbOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    private static final String CREATE_LIST = ""
            + "CREATE TABLE " + WishLists.TABLE + "("
            + WishLists.ID + " INTEGER NOT NULL PRIMARY KEY,"
            + WishLists.NAME + " TEXT NOT NULL"
            + ")";
    private static final String CREATE_ITEM = ""
            + "CREATE TABLE " + MovieItem.TABLE + "("
            + MovieItem.ID + " INTEGER NOT NULL PRIMARY KEY,"
            + MovieItem.LIST_ID + " INTEGER NOT NULL REFERENCES " + MovieItem.TABLE + "(" + MovieItem.ID + "),"
            + MovieItem.TITLE + " TEXT NOT NULL,"
            + MovieItem.PLOT + " TEXT NOT NULL,"
            + MovieItem.ACTORS + " TEXT NOT NULL,"
            + MovieItem.RATING + " TEXT NOT NULL,"
            + MovieItem.POSTER + " TEXT NOT NULL"
            + ")";
    private static final String CREATE_ITEM_LIST_ID_INDEX =
            "CREATE INDEX item_list_id ON " + MovieItem.TABLE + " (" + MovieItem.LIST_ID + ")";

    public DbOpenHelper(Context context) {
        super(context, "favourites.db", null /* factory */, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST);
        db.execSQL(CREATE_ITEM);
        db.execSQL(CREATE_ITEM_LIST_ID_INDEX);

        long myWishListId = db.insert(WishLists.TABLE, null, new WishLists.Builder()
                .name("MyWishlist")
                .build());
        db.insert(MovieItem.TABLE, null, new MovieItem.Builder()
                .listId(myWishListId)
                .title("Kill Bill: Vol. 1")
                .plot("The Bride wakens from a four-year coma. The child she carried in her womb is gone. Now she must wreak vengeance on the team of assassins who betrayed her - a team she was once part of.")
                .actors("Uma Thurman, Lucy Liu, Vivica A. Fox, Daryl Hannah")
                .rating("8.1")
                .poster("http://ia.media-imdb.com/images/M/MV5BMTU1NDg1Mzg4M15BMl5BanBnXkFtZTYwMDExOTc3._V1_SX300.jpg")
                .build());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
