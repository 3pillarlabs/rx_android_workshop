package com.tpg.movierx.util;

import java.text.DateFormat;
import java.util.Date;

import rx.Observable;
import rx.Observer;

/**
 * Log to System.out Rx events, include time and thread name.
 *
 * Created by ciprian.grigor on 24/10/15.
 */
public class RxLog {

    public static void log(Object source, Object item) {
        System.out.println(DateFormat.getTimeInstance().format(new Date(System.currentTimeMillis()))
                + "\t" + source
                + "\t" + Thread.currentThread().getName()
                + "\t" + item);
    }

    public static void log(Object source, Throwable throwable) {
        System.out.println(DateFormat.getTimeInstance().format(new Date(System.currentTimeMillis()))
                + "\t" + source
                + "\t" + Thread.currentThread().getName()
                + "\t" + throwable);
        throwable.printStackTrace();
    }

    public static <T> Observer<T> logObserver() {
        return new Observer<T>() {
            @Override
            public void onCompleted() {
                log(this, "completed");
            }

            @Override
            public void onError(Throwable e) {
                log(this, e);
            }

            @Override
            public void onNext(T t) {
                log(this, t);
            }
        };
    }

    public static <T> Observable.Transformer<T, T> insertLog() {
        return observable -> observable
                .doOnSubscribe(() -> log(observable, "subscribe"))
                .doOnNext(item -> log(observable, item))
                .doOnError(throwable -> log(observable, throwable))
                .doOnCompleted(() -> log(observable, "completed"));
    }
}
