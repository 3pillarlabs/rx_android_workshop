package com.tpg.movierx;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.ListPopupWindow;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbMovie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private static final Logger logger = LoggerFactory.getLogger("MainActivity");

    @Inject
    OmdbApi omdbApi;

    @Bind(R.id.searchText)
    EditText searchText;

    ListPopupWindow popup;
    MoviePopupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.component(this).inject(this);

        setContentView(R.layout.activity_main);
        popup = new ListPopupWindow(this);
        popup.setAnchorView(toolbar);
        adapter = new MoviePopupAdapter();
        popup.setAdapter(adapter);

        RxTextView.textChanges(searchText)
                .map(CharSequence::toString)
                .flatMap(title -> omdbApi.searchByTitle(title).subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .map(omdbSearchMovies -> omdbSearchMovies.movies)
                .subscribe(this::setMovies, this::handleError);
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
