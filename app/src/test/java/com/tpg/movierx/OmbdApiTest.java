package com.tpg.movierx;

import com.tpg.movierx.omdb.DaggerApiComponent;
import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbMovieDetails;
import com.tpg.movierx.omdb.OmdbSearchMovies;
import com.tpg.movierx.util.RxLog;

import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by ciprian.grigor on 07/11/15.
 */
public class OmbdApiTest {

    OmdbApi api;

    @Before
    public void setUp() {
        api = DaggerApiComponent.create().getOmbdApi();
    }


    @Test
    public void searchByTitle() {
        TestSubscriber<OmdbSearchMovies> sub = new TestSubscriber<>();

        api.searchByTitle("star").compose(RxLog::log).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);
    }

    @Test
    public void getByTitle() {
        TestSubscriber<OmdbMovieDetails> sub = new TestSubscriber<>();

        api.getByTitle("Star Wars: The Force Awakens").compose(RxLog::log).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);

        OmdbMovieDetails movie = sub.getOnNextEvents().get(0);
        assertEquals("Star Wars: The Force Awakens", movie.title);
        assertEquals("2015", movie.year);
    }


    @Test
    public void getByTitleNotFound() {
        TestSubscriber<OmdbMovieDetails> sub = new TestSubscriber<>();

        api.getByTitle("Star Wars: Episode XX").compose(RxLog::log).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);

    }

    @Test
    public void searchByTitleNotFound() {
        TestSubscriber<OmdbSearchMovies> sub = new TestSubscriber<>();

        api.searchByTitle("Star Wars: Episode XX").compose(RxLog::log).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);
        assertFalse(sub.getOnNextEvents().get(0).success);
    }
}
