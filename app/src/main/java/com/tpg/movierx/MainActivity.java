package com.tpg.movierx;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListPopupWindow;

import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbMovie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.Bind;

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

        adapter.setMovieList(Arrays.asList(new OmdbMovie("Title 1"), new OmdbMovie("Title 2")));

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    omdbApi.searchByTitle(s.toString()).subscribe(omdbSearchMovies -> {
                        adapter.setMovieList(omdbSearchMovies.movies);
                        popup.show();
                    });
                } else {
                    popup.dismiss();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
