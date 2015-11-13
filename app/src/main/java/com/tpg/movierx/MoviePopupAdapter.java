package com.tpg.movierx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbMovie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ciprian.grigor on 04/11/15.
 */
public class MoviePopupAdapter extends BaseAdapter {

    private static final Logger logger = LoggerFactory.getLogger("MoviePopupAdapter");
    private final LayoutInflater inflater;

    @Inject
    OmdbApi api;

    List<OmdbMovie> data;

    public MoviePopupAdapter(Context context) {
        super();
        MovieApplication.component(context).inject(this);
        inflater = LayoutInflater.from(context);
    }

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
            view = inflater.inflate(R.layout.movie_dropdown_item, parent, false);
        } else {
            view = convertView;
        }

        ((TextView) view).setText(getItem(position).toString());

        return view;
    }

    public void setMovieList(List<OmdbMovie> movieList) {
        data = movieList;
        this.notifyDataSetChanged();
    }
}