package com.tpg.movierx.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tpg.movierx.R;
import com.tpg.movierx.db.MovieItem;

import java.util.Collections;
import java.util.List;

import rx.functions.Action1;

/**
 * Handles a collection of MovieItems
 * <p>
 * Created by karoly.szanto on 07/11/15.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MovieCardHolder> implements Action1<List<MovieItem>> {

    private List<MovieItem> items = Collections.emptyList();

    private final LayoutInflater inflater;

    public MoviesAdapter(final Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void call(List<MovieItem> movieItems) {
        this.items = movieItems;
        notifyDataSetChanged();
    }

    @Override
    public MovieCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = inflater.inflate(R.layout.movie_item_card_layout, parent, false);
        return new MovieCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieCardHolder holder, int position) {
        holder.map(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public MovieItem getItemAt(final int position) {
        return items.get(position);
    }

    public void insertItemAt(final MovieItem item, final int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItemAt(final int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }
}
