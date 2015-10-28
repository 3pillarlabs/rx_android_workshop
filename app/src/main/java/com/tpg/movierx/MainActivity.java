package com.tpg.movierx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tpg.movierx.omdb.OmdbApiService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logEvenNumbers(new Integer[]{1, 3, 4, 5, 6});
        logMoviePlotByTitle("Kill Bill");
    }

    private void logEvenNumbers(final Integer[] numbers) {

        DummyObservables
                .keepEvenNumbers(numbers)
                .subscribe(i -> logger.debug("{}", i));
    }

    private void logMoviePlotByTitle(final String movieTitle) {
        OmdbApiService
                .getService()
                .findByTitle(OmdbApiService.getFindByTitleOptions(movieTitle))
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
