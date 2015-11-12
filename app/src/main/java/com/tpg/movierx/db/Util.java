package com.tpg.movierx.db;

/**
 * Created by karoly.szanto on 11/11/15.
 */
public class Util {

    public static final String ALL_WISHLISTS_QUERY = "SELECT * FROM "
            + WishLists.TABLE;

    public static final String ALL_MOVIES_QUERY = "SELECT * FROM "
            + MovieItem.TABLE
            + " = ? ORDER BY "
            + MovieItem.TITLE
            + " ASC";

    public static final String MOVIES_IN_WISHLIST_QUERY = "SELECT * FROM "
            + MovieItem.TABLE
            + " WHERE "
            + MovieItem.LIST_ID
            + " = ? ORDER BY "
            + MovieItem.ID
            + " ASC";
}
