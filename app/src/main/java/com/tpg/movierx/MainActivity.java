package com.tpg.movierx;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListPopupWindow;

import com.tpg.movierx.omdb.OmdbApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    private static final Logger logger = LoggerFactory.getLogger("MainActivity");
    @Inject
    OmdbApi api;


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

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    api.searchByTitle(s.toString()).subscribe(omdbSearchMovies -> {
                        adapter.setMovieList(omdbSearchMovies.movies);
                        popup.show();
                    });
                } else {
                    adapter.setMovieList(null);
                    popup.dismiss();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
