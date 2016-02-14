package com.tpg.movierx.util;

import java.text.DateFormat;
import java.util.Date;

import rx.Observable;

/**
 * Log to System.out Rx events, include time and thread name.
 * <p>
 * Created by ciprian.grigor on 24/10/15.
 */
public class RxLog {
    public static void log(Object source, String item) {
        System.out.println(DateFormat.getTimeInstance().format(new Date(System.currentTimeMillis()))
                + "\t" + source
                + "\t" + Thread.currentThread().getName()
                + "\t" + item);
    }

    public static void logNext(Object source, Object item) {
        System.out.println(DateFormat.getTimeInstance().format(new Date(System.currentTimeMillis()))
                + "\t" + source
                + "\t" + Thread.currentThread().getName()
                + "\t" + "onNext: " + item);
    }

    public static void logError(Object source, Throwable throwable) {
        System.out.println(DateFormat.getTimeInstance().format(new Date(System.currentTimeMillis()))
                + "\t" + source
                + "\t" + Thread.currentThread().getName()
                + "\t" + "onError: " + throwable);
        //throwable.printStackTrace();
    }

    public static <T> Observable<T> log(final Observable<T> observable) {
        return log(observable, observable.toString());
    }

    public static <T> Observable<T> log(final Observable<T> observable, final String name) {
        return observable
                .doOnSubscribe(() -> log(name, "subscribe"))
                //.doOnRequest(n -> log(name, "request " + n))
                .doOnNext(item -> logNext(name, item))
                .doOnError(throwable -> logError(name, throwable))
                .doOnCompleted(() -> log(name, "completed"))
                .doOnUnsubscribe(() -> log(name, "unsubscribe"));

    }
}
