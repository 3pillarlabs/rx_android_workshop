package com.tpg.movierx.exercise1;

import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Created by karoly.szanto on 16/02/16.
 */
public class ExploreSimpleOperatorsTest {

    /**
     * Test1: simple observable creation
     */
    @Test
    public void test1_emitNumbers_from_1_to_5() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        ExploreSimpleOperators
                .createIntRangeOperator()
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertCompleted();
        testSubscriber.assertUnsubscribed();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(5);
        testSubscriber.assertValues(1, 2, 3, 4, 5);
    }

    /**
     * Test2: filtering observables
     */
    @Test
    public void test2_emit_only_stringStartingWith_theLetter_q() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();

        ExploreSimpleOperators
                .onlyStringsStartingWithALetter()
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertCompleted();
        testSubscriber.assertUnsubscribed();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(2);
        testSubscriber.assertValues(ExploreSimpleOperators.EX_STRINGS[0], ExploreSimpleOperators.EX_STRINGS[4]);
    }

    /**
     * Test3: transforming observables
     */
    @Test
    public void test3_emitTheLength_ofEachString() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        ExploreSimpleOperators
                .emitTheLengthOfEachString()
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertCompleted();
        testSubscriber.assertUnsubscribed();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(5);
        testSubscriber.assertValues(
                ExploreSimpleOperators.EX_STRINGS[0].length(),
                ExploreSimpleOperators.EX_STRINGS[1].length(),
                ExploreSimpleOperators.EX_STRINGS[2].length(),
                ExploreSimpleOperators.EX_STRINGS[3].length(),
                ExploreSimpleOperators.EX_STRINGS[4].length()
        );
    }

    /**
     * Test4: filtering observables, a bit more advanced
     */
    @Test
    public void test4_emitTheFirstFive_evenNumbers_from_1_to_100() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        ExploreSimpleOperators
                .emitTheFirst5EvenNumbersFrom1to100()
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertCompleted();
        testSubscriber.assertUnsubscribed();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(5);
        testSubscriber.assertValues(2, 4, 6, 8, 10);
    }

    /**
     * Test5: filtering observables and aggregation
     */
    @Test
    public void test5_computeTheSumOf_allEvenNumbers_from_1_to_100() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        ExploreSimpleOperators
                .emitTheSumOfTheFirst5EvenNumbersFrom1to100()
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertCompleted();
        testSubscriber.assertUnsubscribed();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValues(30);
    }

    /**
     * Test6: combining operators
     */
    @Test
    public void test6_observableShouldEmitOnlyThe_firstItemOfTheFirstSourceObservable_and_theLastItemOfTheSecondSourceObservable() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        ExploreSimpleOperators
                .emitTheFirstAndLastItems(
                        Observable.range(1, 50),
                        Observable.range(51, 50)
                )
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertCompleted();
        testSubscriber.assertUnsubscribed();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(2);
        testSubscriber.assertValues(1, 100);
    }
}
