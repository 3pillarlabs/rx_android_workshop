package com.tpg.movierx.omdb;

/**
 * An extension of {@link com.tpg.movierx.omdb.OmdbMovie} with more details
 */

import com.google.gson.annotations.SerializedName;

public class OmdbMovieDetails extends OmdbMovie {

    @SerializedName("Rated")
    public String rated;

    @SerializedName("Runtime")
    public String runtime;

    @SerializedName("Director")
    public String director;

    @SerializedName("Writer")
    public String writer;

    @SerializedName("Actors")
    public String actors;

    @SerializedName("Plot")
    public String plot;

    public String imdbRating;

    @Override
    public String toString() {
        return "OmdbMovieDetails{" +
                "actors='" + actors + '\'' +
                ", rated='" + rated + '\'' +
                ", runtime='" + runtime + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", plot='" + plot + '\'' +
                "} " + super.toString();
    }
}
