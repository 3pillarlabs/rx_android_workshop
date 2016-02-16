package com.tpg.movierx.exercise1;

import rx.Observable;

/**
 * Created by karoly.szanto on 16/02/16.
 */
public class ExploreSimpleOperators {

    public static final String[] EX_STRINGS = new String[] { "qvovp", "ctm5t1", "ngy", "9whgggs6", "qo14a" };

    /**
     * @return an observable which emits numbers from 1 to 5
     */
    public static Observable<Integer> createIntRangeOperator() {
        return null;
    }

    /**
     * @return an observable which emits only the strings starting with the letter "q"
     */
    public static Observable<String> onlyStringsStartingWithALetter() {
        return createStringTestObservable();
    }

    /**
     * @return an observable which emits the length of each string starting with the observable given by the createStringTestObservable
     */
    public static Observable<Integer> emitTheLengthOfEachString() {

        /*
         * Please start by replacing the line bellow with "return createStringTestObservable()"
         */
        return null;
    }

    /**
     * @return and observable which emits the sum of the first 5 even numbers
     */
    public static Observable<Integer> emitTheFirst5EvenNumbersFrom1to100() {
        return Observable.range(1, 100);
    }

    /**
     * @return and observable which emits the sum of the first 5 even numbers from 1 to 100
     */
    public static Observable<Integer> emitTheSumOfTheFirst5EvenNumbersFrom1to100() {
        return emitTheFirst5EvenNumbersFrom1to100();
    }

    /**
     * @return and observable which emits the first item emitted by the first source observable and the
     * last item emitted by the last observable
     */
    public static Observable<Integer> emitTheFirstAndLastItems(final Observable<Integer> obs1, final Observable<Integer> obs2) {
        return null;
    }

    /* utility methods */
    private static Observable<String> createStringTestObservable() {
        return Observable.from(EX_STRINGS);
    }
}
