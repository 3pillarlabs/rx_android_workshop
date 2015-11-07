package com.tpg.movierx.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.squareup.sqlbrite.BriteDatabase;
import com.tpg.movierx.MovieApplication;
import com.tpg.movierx.R;
import com.tpg.movierx.db.MovieItem;
import com.tpg.movierx.db.Util;
import com.tpg.movierx.omdb.OmdbApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final Logger logger = LoggerFactory.getLogger("MainActivity");

    @Inject
    OmdbApi api;

    @Inject
    BriteDatabase db;

    @Bind(R.id.movies_list)
    MoviesRecycler moviesRecycler;

    @Bind(R.id.empty_recycler)
    View emptyRecyclerView;

    private LinearLayoutManager cardListLayoutManager;

    private MoviesAdapter adapter;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.component(this).inject(this);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        logMoviePlotByTitle("Kill Bill");

        adapter = new MoviesAdapter(this);

        cardListLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        moviesRecycler.setLayoutManager(cardListLayoutManager);
        moviesRecycler.setEmptyView(emptyRecyclerView);
        moviesRecycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        subscription = db.createQuery(MovieItem.TABLE, Util.MOVIES_IN_WISHLIST_QUERY, "1")
                .mapToList(MovieItem.MAPPER)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);
    }

    @Override
    protected void onDestroy() {

        if(subscription != null) {
            subscription.unsubscribe();
        }

        super.onDestroy();
    }

    private void logMoviePlotByTitle(final String movieTitle) {
        api
                .findByTitle(movieTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        movie -> {
                            logger.debug("Title: {} | Plot: {}", movie.getTitle(), movie.getPlot());
                        },
                        error -> logger.error(error.getMessage())
                );
    }
}
