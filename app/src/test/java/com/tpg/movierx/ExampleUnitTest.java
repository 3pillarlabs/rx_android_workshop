package com.tpg.movierx;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testObservable() {
        final StringBuilder result = new StringBuilder();

        Observable.just(1, 2, 3).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                msg("onCompleted");
                result.append('|');
            }

            @Override
            public void onError(Throwable e) {
                msg("onError " + e);
                result.append('X');
            }

            @Override
            public void onNext(Integer integer) {
                msg("onNext " + integer);
                result.append(integer);
            }
        });

        assertEquals("subcriber callbacks", "123|", result.toString());
    }

    private void msg(String message) {
        System.out.println(message);
    }
}