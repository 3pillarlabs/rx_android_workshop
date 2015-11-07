package com.tpg.movierx;

import com.tpg.movierx.di.ApplicationComponent;
import com.tpg.movierx.di.ApplicationModule;
import com.tpg.movierx.di.DaggerApplicationComponent;
import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.omdb.OmdbMovie;
import com.tpg.movierx.omdb.OmdbSearchMovies;
import com.tpg.movierx.util.RxLog;

import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;

import static org.mockito.Mockito.mock;

/**
 * Created by ciprian.grigor on 07/11/15.
 */
public class OmbdApiTest {

    OmdbApi api;

    @Before
    public void setUp() {
        MovieApplication app = mock(MovieApplication.class);

        ApplicationComponent component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(app))
                .build();

        api = component.getOmbdApi();
    }


    @Test
    public void searchByTitle() {
        TestSubscriber<OmdbSearchMovies> sub = new TestSubscriber<>();

        api.searchByTitle("star").compose(RxLog.logObservable()).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);
    }

    @Test
    public void getByTitle() {
        TestSubscriber<OmdbMovie> sub = new TestSubscriber<>();

        api.getByTitle("Star Wars: Episode VII - The Force Awakens").compose(RxLog.logObservable()).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);

    }


    @Test
    public void getByTitleNotFound() {
        TestSubscriber<OmdbMovie> sub = new TestSubscriber<>();

        api.getByTitle("Star Wars: Episode XX").compose(RxLog.logObservable()).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);

    }

    @Test
    public void searchByTitleNotFound() {
        TestSubscriber<OmdbSearchMovies> sub = new TestSubscriber<>();

        api.searchByTitle("Star Wars: Episode XX").compose(RxLog.logObservable()).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);
    }
}
