package com.tpg.movierx.ui;

import android.database.Cursor;

import com.tpg.movierx.ui.AutoParcel_WishListItem;
import com.tpg.movierx.db.Db;
import com.tpg.movierx.db.MovieItem;
import com.tpg.movierx.db.WishLists;

import java.util.Arrays;
import java.util.Collection;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

/**
 * Helper to fetch all WishLists together with their respective count of associated
 * MovieItem collection
 */
@AutoParcel
public abstract class WishListItem {
    private static String ALIAS_LIST = "list";
    private static String ALIAS_ITEM = "item";

    private static String LIST_ID = ALIAS_LIST + "." + WishLists.ID;
    private static String LIST_NAME = ALIAS_LIST + "." + WishLists.NAME;
    private static String ITEM_COUNT = "item_count";
    private static String ITEM_ID = ALIAS_ITEM + "." + MovieItem.ID;
    private static String ITEM_LIST_ID = ALIAS_ITEM + "." + MovieItem.LIST_ID;

    public static Collection<String> TABLES = Arrays.asList(WishLists.TABLE, MovieItem.TABLE);
    public static String QUERY = ""
            + "SELECT " + LIST_ID + ", " + LIST_NAME + ", COUNT(" + ITEM_ID + ") as " + ITEM_COUNT
            + " FROM " + WishLists.TABLE + " AS " + ALIAS_LIST
            + " LEFT OUTER JOIN " + MovieItem.TABLE + " AS " + ALIAS_ITEM + " ON " + LIST_ID + " = " + ITEM_LIST_ID
            + " GROUP BY " + LIST_ID;

    public abstract long id();

    public abstract String name();

    public abstract int itemCount();

    public static Func1<Cursor, WishListItem> MAPPER = cursor -> {
        long id = Db.getLong(cursor, WishLists.ID);
        String name = Db.getString(cursor, WishLists.NAME);
        int itemCount = Db.getInt(cursor, ITEM_COUNT);
        return new AutoParcel_WishListItem(id, name, itemCount);
    };
}
