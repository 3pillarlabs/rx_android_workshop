package com.tpg.movierx.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tpg.movierx.R;
import com.tpg.movierx.db.MovieItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by karoly.szanto on 07/11/15.
 */
public class MovieCardHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.poster)
    ImageView posterView;

    @Bind(R.id.title)
    TextView titleView;

    @Bind(R.id.actors)
    TextView actorsView;

    public MovieCardHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void map(final MovieItem item, final int position) {
        titleView.setText(item.title());
        actorsView.setText(item.actors());

        Glide.with(itemView.getContext())
                .load(item.poster())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(posterView);
    }
}
