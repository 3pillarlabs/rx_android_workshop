package com.tpg.movierx;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.ListPopupWindow;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.sqlbrite.BriteDatabase;
import com.tpg.movierx.db.Util;
import com.tpg.movierx.db.WishLists;
import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbMovie;

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

        db.createQuery(WishLists.TABLE, Util.ALL_WISHLISTS_QUERY)
                .mapToList(WishLists.MAPPER)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> logger.debug("Size {}", list.size()));
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
