package com.tpg.movierx;

import com.tpg.movierx.di.DaggerApiComponent;
import com.tpg.movierx.omdb.OmdbApi;
import com.tpg.movierx.util.RxLog;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

/**
 * Created by ciprian.grigor on 10/11/15.
 */
public class SearchTest {
    OmdbApi api;

    @Before
    public void setUp() {
        api = DaggerApiComponent.create().getOmbdApi();
    }


    @Test
    public void fastInput() throws InterruptedException {

        CharSequence input = "Empire at";

        Observable<CharSequence> obs = Observable.interval(20, TimeUnit.MILLISECONDS).map(Long::intValue).take(input.length()).skip(1).map(end -> input.subSequence(0, end+1));

        TestSubscriber<String> subscriber = new TestSubscriber<>();

        obs.map(CharSequence::toString)
                .flatMap(title -> api.searchByTitle(title).compose(RxLog.insertLog()).subscribeOn(Schedulers.io()))
                .compose(RxLog.insertLog())
                .filter(res -> res.success)
                .map(res -> res.movies.get(0).title)
                .observeOn(Schedulers.newThread())
                .compose(RxLog.insertLog())
                .subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();
    }
}
