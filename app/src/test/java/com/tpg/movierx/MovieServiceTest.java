package com.tpg.movierx;

import com.tpg.movierx.db.dao.MovieItemDao;
import com.tpg.movierx.omdb.DaggerApiComponent;
import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbSearchMovies;
import com.tpg.movierx.service.MovieService;
import com.tpg.movierx.util.RxLog;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.mock;

/**
 * Created by ciprian.grigor on 16/02/16.
 */
public class MovieServiceTest {

    MovieService movieService;
    MovieItemDao movieItemDao;

    @Before
    public void setUp() {
        OmdbApi omdbApi = DaggerApiComponent.create().getOmbdApi();
        movieItemDao = mock(MovieItemDao.class);
        movieService = new MovieService(omdbApi, movieItemDao);
    }

    @Test
    public void last_title_should_match_search() throws InterruptedException {

        CharSequence input = "empire at";

        Observable<String> obs = Observable.interval(100, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .take(input.length())
                .map(end -> input.subSequence(0, end + 1)).map(CharSequence::toString);

        TestSubscriber<String> subscriber = new TestSubscriber<>();

        obs.compose(movieService::searchMovie)
                .map(this::extractFirstTitle)
                .compose(RxLog::log)
                .subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();
        Assert.assertTrue(subscriber.getOnNextEvents().get(0).toLowerCase().contains("empire at"));
    }

    String extractFirstTitle(OmdbSearchMovies searchMovies) {
        if (searchMovies.movies == null || searchMovies.movies.isEmpty()) {
            return null;
        } else {
            return searchMovies.movies.get(0).title;
        }
    }
}
