package com.tpg.movierx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logEvenNumbers(new Integer[]{ 1, 3, 4, 5, 6 });
    }

    private void logEvenNumbers(final Integer[] numbers) {

        DummyObservables
                .keepEvenNumbers(numbers)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        logger.debug("{}", integer);
                    }
                });
    }
}
