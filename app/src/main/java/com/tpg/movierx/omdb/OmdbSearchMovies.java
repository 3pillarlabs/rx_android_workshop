package com.tpg.movierx.omdb;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * {
 *      Search: [
 *          {...},
 *          {...}
 *      ]
 * }
 */
public class OmdbSearchMovies {

    @SerializedName("Search")
    public List<OmdbMovie> movies;
}
