package com.tpg.movierx;

import org.junit.Test;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Created by karoly.szanto on 28/10/15.
 */
public class DummyObservablesTest {

    @Test
    public void keepEvenNumbers_should_return_a_list_of_even_numbers_only() {

        final Observable<Integer> obs = DummyObservables.keepEvenNumbers(new Integer[]{1, 2, 3, 4, 5, 6});
        final TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        obs.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        final List<Integer> result = testSubscriber.getOnNextEvents();

        for (int i : result) {
            assertTrue(i % 2 == 0);
        }
    }
}
