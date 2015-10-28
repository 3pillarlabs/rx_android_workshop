package com.tpg.movierx.omdb;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karoly.szanto on 24/10/15.
 */
public class OmdbMovie {

    @SerializedName("Title")
    private String title;
    @SerializedName("Plot")
    private String plot;

    public String getTitle() {
        return title;
    }

    public String getPlot() {
        return plot;
    }
}
