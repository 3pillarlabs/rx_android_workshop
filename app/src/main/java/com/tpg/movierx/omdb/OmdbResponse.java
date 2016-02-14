package com.tpg.movierx.omdb;

import com.google.gson.annotations.SerializedName;

/**
 * Base for any omdb response
 */
public class OmdbResponse {
    @SerializedName("Response")
    public boolean success = true;

    @SerializedName("Error")
    public String errorMessage;

    @Override
    public String toString() {
        return "OmdbResponse{" +
                "success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
