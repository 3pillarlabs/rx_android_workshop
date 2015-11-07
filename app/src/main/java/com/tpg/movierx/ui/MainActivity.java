package com.tpg.movierx.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tpg.movierx.MovieApplication;
import com.tpg.movierx.R;
import com.tpg.movierx.db.MovieItem;
import com.tpg.movierx.omdb.OmdbApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final Logger logger = LoggerFactory.getLogger("MainActivity");

    @Inject
    OmdbApi api;

    @Bind(R.id.movies_list)
    MoviesRecycler moviesRecycler;

    @Bind(R.id.empty_recycler)
    View emptyRecyclerView;

    private LinearLayoutManager cardListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.component(this).inject(this);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        logMoviePlotByTitle("Kill Bill");

        final MoviesAdapter adapter = new MoviesAdapter(this, MovieItem.createDummyMovieList(30));

        cardListLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        moviesRecycler.setLayoutManager(cardListLayoutManager);
        moviesRecycler.setEmptyView(emptyRecyclerView);
        moviesRecycler.setAdapter(adapter);
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
