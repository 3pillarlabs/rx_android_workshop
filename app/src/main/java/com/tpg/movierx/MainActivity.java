package com.tpg.movierx;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.ListPopupWindow;

import com.jakewharton.rxbinding.support.design.widget.RxSnackbar;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.sqlbrite.BriteDatabase;
import com.tpg.movierx.db.MovieItem;
import com.tpg.movierx.db.Util;
import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbMovie;
import com.tpg.movierx.ui.MoviesAdapter;
import com.tpg.movierx.ui.MoviesRecycler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private static final Logger logger = LoggerFactory.getLogger("MainActivity");
    @Inject
    OmdbApi api;

    @Inject
    BriteDatabase db;

    @Bind(R.id.searchText)
    EditText searchText;

    @Bind(R.id.movies_list)
    MoviesRecycler moviesRecycler;

    @Bind(R.id.empty_recycler)
    View emptyRecyclerView;

    private LinearLayoutManager cardListLayoutManager;

    private MoviesAdapter moviesListAdapter;

    ListPopupWindow popup;
    private MoviePopupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.component(this).inject(this);
        setContentView(R.layout.activity_main);
        popup = new ListPopupWindow(this);
        popup.setAnchorView(toolbar);

        adapter = new MoviePopupAdapter(this);

        popup.setAdapter(adapter);

        moviesListAdapter = new MoviesAdapter(this);
        cardListLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        moviesRecycler.setLayoutManager(cardListLayoutManager);
        moviesRecycler.setEmptyView(emptyRecyclerView);
        moviesRecycler.setAdapter(moviesListAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                final Snackbar snackBar = Snackbar.make(moviesRecycler, R.string.removed_from_wishlist, Snackbar.LENGTH_SHORT);
                snackBar.setAction(R.string.undo_remove, (view) -> {});
                snackBar.show();

                RxSnackbar.dismisses(snackBar)
                        .subscribe(eventId -> { logger.debug("Dismiss event: {})", eventId); /* Check Snackbar.Callback */ });
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(moviesRecycler);
    }

    @Override
    protected void onStart() {
        super.onStart();
        RxTextView.textChanges(searchText)
                .debounce(250, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .flatMap(title -> api.searchByTitle(title).subscribeOn(Schedulers.io()))
                .map(omdbSearchMovies -> omdbSearchMovies.movies)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(this::setMovies, this::handleError);

        db.createQuery(MovieItem.TABLE, Util.MOVIES_IN_WISHLIST_QUERY, "1")
                .mapToList(MovieItem.MAPPER)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moviesListAdapter);
    }

    private void handleError(Throwable throwable) {
        logger.error("searching error", throwable);
        Snackbar.make(searchText, throwable.toString(), Snackbar.LENGTH_LONG).show();

    }

    void setMovies(List<OmdbMovie> movies) {
        adapter.setMovieList(movies);
        popup.show();
    }
}
