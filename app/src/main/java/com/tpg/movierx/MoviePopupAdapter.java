package com.tpg.movierx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpg.movierx.omdb.OmdbMovie;

import java.util.List;

/**
 * Created by ciprian.grigor on 04/11/15.
 */
public class MoviePopupAdapter extends BaseAdapter {
    List<OmdbMovie> data;

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public OmdbMovie getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).imdbId.hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.movie_dropdown_item, parent, false);
        } else {
            view = convertView;
        }

        ((TextView) view).setText(getItem(position).toString());

        return view;
    }

    public void setMovieList(List<OmdbMovie> omdbMovies) {
        data = omdbMovies;
        this.notifyDataSetChanged();
    }
}