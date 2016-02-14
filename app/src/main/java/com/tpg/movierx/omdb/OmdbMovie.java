package com.tpg.movierx.omdb;

import com.google.gson.annotations.SerializedName;

import java.net.URI;

/**
 * {
 * Title:"Star Wars: Episode IV - A New Hope",
 * Year:"1977",
 * imdbID:"tt0076759",
 * Type:"movie",
 * Poster:"http://ia.media-imdb.com/images/M/MV5BMTU4NTczODkwM15BMl5BanBnXkFtZTcwMzEyMTIyMw@@._V1_SX300.jpg"
 * }
 */

public class OmdbMovie extends OmdbResponse {

    @SerializedName("Title")
    public String title;

    @SerializedName("Year")
    public String year;

    @SerializedName("imdbID")
    public String imdbId;

    @SerializedName("Type")
    public Type type;

    @SerializedName("Poster")
    public URI posterUri;

    public OmdbMovie() {
    }

    public OmdbMovie(String title) {
        super();
        this.title = this.imdbId = title;
    }

    @Override
    public String toString() {
        return title + " (" + year + ')';
    }


    public enum Type {
        movie, series, episode
    }
}
