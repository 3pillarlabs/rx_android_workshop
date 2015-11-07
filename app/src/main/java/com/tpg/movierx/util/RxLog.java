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

    public static <T> void log(T item) {
        System.out.println(DateFormat.getTimeInstance().format(new Date(System.currentTimeMillis())) + "\t" + Thread.currentThread().getName() + "\t" + item);
    }

    public static <T> T logAndReturn(T item) {
        log(item);
        return item;
    }

    public static <T> Observer<T> logObeserver() {
        return new Observer<T>() {
            @Override
            public void onCompleted() {
               log(this + " onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log(this + " onError " + e);
            }

            @Override
            public void onNext(T t) {
                log(this + " onNext " + t);
            }
        };
    }

    public static <T> Observable.Transformer<T,T> logObservable() {
        return observable -> observable.doOnNext(RxLog::log).doOnError(RxLog::log).doOnCompleted(() -> RxLog.log("completed"));
    }
}
