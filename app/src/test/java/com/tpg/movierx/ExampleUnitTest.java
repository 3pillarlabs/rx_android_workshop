package com.tpg.movierx;

import com.google.gson.Gson;
import com.tpg.movierx.omdb.OmdbResponse;
import com.tpg.movierx.util.RxLog;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void stringEncodign() throws Exception {
        Gson gson = new Gson();
        OmdbResponse obj = new OmdbResponse();
        obj.errorMessage = "var x = function(int x, in y) {\n" +
                " return \"response\" }";

        String json = gson.toJson(obj);
        msg(json);

        msg(gson.fromJson(json, OmdbResponse.class).toString());

    }
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

    @Test
    public void testIntervalObservable() {
        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();

        Observable.interval(500, TimeUnit.MILLISECONDS)
                .filter(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long number) {
                        return number % 2 == 0;
                    }
                })
                .take(5)
                .compose(RxLog::log)
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertCompleted();
        testSubscriber.assertUnsubscribed();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(5);
        testSubscriber.assertValues(0L, 2L, 4L, 6L, 8L);
    }

    private void msg(String message) {
        System.out.println(message);
    }
}