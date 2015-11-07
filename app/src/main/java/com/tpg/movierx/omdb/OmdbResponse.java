package com.tpg.movierx.omdb;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ciprian.grigor on 07/11/15.
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
