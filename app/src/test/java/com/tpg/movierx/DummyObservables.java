package com.tpg.movierx;

import rx.Observable;

/**
 * Created by karoly.szanto on 28/10/15.
 */
public class DummyObservables {

    public static Observable<Integer> keepEvenNumbers(final Integer[] numbers) {

        return Observable
                .from(numbers)
                .filter(i -> i % 2 == 0);
    }
}
