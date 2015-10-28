package com.tpg.movierx;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by karoly.szanto on 28/10/15.
 */
public class DummyObservables {

    public static Observable<Integer> keepEvenNumbers(final Integer[] numbers) {

        return Observable
                .from(numbers)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                });
    }
}
