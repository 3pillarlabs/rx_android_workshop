package com.tpg.movierx;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

/**
 * Created by karoly.szanto on 28/10/15.
 */
public class DummyObservablesTest {

    @Test
    public void observableEvents() {

        final Observable<Integer> obs = Observable.just(1, 2, 3, 4, 5);

        obs.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("next " + integer);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("error " + throwable);
            }
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("completed");
            }
        });
    }

    @Test
    public void evenNumberObservable() {
        Observable<Long> obs = Observable.interval(500, TimeUnit.MILLISECONDS)
                .take(10)
                .filter(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long tick) {
                        return tick % 2 == 0;
                    }
                })
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long number) {
                        System.out.println("next " + number);
                    }
                });

        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();

        obs.subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(5);
    }
}
