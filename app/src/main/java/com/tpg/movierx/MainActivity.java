package com.tpg.movierx;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListPopupWindow;

import com.jakewharton.rxbinding.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.tpg.movierx.db.dao.MovieItemDao;
import com.tpg.movierx.omdb.OmdbSearchMovies;
import com.tpg.movierx.rxbinding.RxListPopupWindow;
import com.tpg.movierx.service.MovieService;
import com.tpg.movierx.ui.MoviesAdapter;
import com.tpg.movierx.ui.MoviesRecycler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {

    private static final Logger logger = LoggerFactory.getLogger("MainActivity");

    @Inject
    MovieService movieService;

    @Inject
    MovieItemDao movieItemDao;

    @Bind(R.id.searchText)
    EditText searchText;

    @Bind(R.id.movies_list)
    MoviesRecycler moviesRecycler;

    @Bind(R.id.empty_recycler)
    View emptyRecyclerView;

    ListPopupWindow popup;
    MoviePopupAdapter adapter;

    Snackbar searchErrorSnackbar;

    private LinearLayoutManager cardListLayoutManager;
    private MoviesAdapter moviesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.component(this).inject(this);

        setContentView(R.layout.activity_main);
        popup = new ListPopupWindow(this);
        popup.setAnchorView(toolbar);
        adapter = new MoviePopupAdapter();
        popup.setAdapter(adapter);

        moviesListAdapter = new MoviesAdapter(this);
        cardListLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        moviesRecycler.setLayoutManager(cardListLayoutManager);
        moviesRecycler.setEmptyView(emptyRecyclerView);
        moviesRecycler.setAdapter(moviesListAdapter);

        RxListPopupWindow.itemClickEvents(popup)
                .map(AdapterViewItemClickEvent::position)
                .map(adapter::getItem)
                .subscribe(movie -> {
                    popup.dismiss();
                    Snackbar.make(searchText, movie + " selected", Snackbar.LENGTH_LONG).show();
                });

        RxListPopupWindow.itemClickEvents(popup)
                .map(AdapterViewItemClickEvent::position)
                .map(adapter::getItem)
                .onBackpressureDrop(item -> logger.info("backpressure drop {}", item))
                .flatMap(movieService::saveMovie, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        notif -> {
                            if (notif.isOnNext()) {
                                popup.dismiss();
                                moviesRecycler.smoothScrollToPosition(moviesListAdapter.getItemCount());
                            }
                            if (notif.hasThrowable()) {
                                Snackbar.make(searchText, notif.getThrowable().getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                );
    }

    @Override
    protected void onStart() {
        super.onStart();
        RxTextView.textChanges(searchText)
                .filter(text -> !TextUtils.isEmpty(text))
                .map(CharSequence::toString)
                .compose(movieService::searchMovie)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(this::setMoviesResult, this::handleError);

        movieItemDao.allMovieItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moviesListAdapter);
    }

    void handleError(Throwable throwable) {
        logger.error("searching error", throwable);
        Snackbar.make(searchText, throwable.toString(), Snackbar.LENGTH_LONG).show();
    }


    void setMoviesResult(OmdbSearchMovies movieResult) {
        adapter.setMovieList(movieResult.movies);
        if (movieResult.success) {
            popup.show();
            if (searchErrorSnackbar != null) {
                searchErrorSnackbar.dismiss();
                searchErrorSnackbar = null;
            }
        } else {
            popup.dismiss();
            searchErrorSnackbar = Snackbar.make(searchText, movieResult.errorMessage, Snackbar.LENGTH_LONG);
            searchErrorSnackbar.show();
        }
    }

}
