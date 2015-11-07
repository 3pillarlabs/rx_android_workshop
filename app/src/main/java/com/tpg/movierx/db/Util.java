package com.tpg.movierx.db;

/**
 * Created by karoly.szanto on 07/11/15.
 */
public class Util {

    public static final String MOVIES_IN_WISHLIST_QUERY = "SELECT * FROM "
            + MovieItem.TABLE
            + " WHERE "
            + MovieItem.LIST_ID
            + " = ? ORDER BY "
            + MovieItem.TITLE
            + " ASC";
}
