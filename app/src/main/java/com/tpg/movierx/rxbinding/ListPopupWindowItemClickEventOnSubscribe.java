package com.tpg.movierx.rxbinding;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListPopupWindow;

import com.jakewharton.rxbinding.internal.MainThreadSubscription;
import com.jakewharton.rxbinding.widget.AdapterViewItemClickEvent;

import rx.Observable;
import rx.Subscriber;

import static com.jakewharton.rxbinding.internal.Preconditions.checkUiThread;

/**
 * Created by ciprian.grigor on 13/11/15.
 */

final class ListPopupWindowItemClickEventOnSubscribe
        implements Observable.OnSubscribe<AdapterViewItemClickEvent> {
    private final ListPopupWindow list;

    public ListPopupWindowItemClickEventOnSubscribe(ListPopupWindow list) {
        this.list = list;
    }

    @Override
    public void call(final Subscriber<? super AdapterViewItemClickEvent> subscriber) {
        checkUiThread();

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(AdapterViewItemClickEvent.create(parent, view, position, id));
                }
            }
        };
        list.setOnItemClickListener(listener);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                list.setOnItemClickListener(null);
            }
        });
    }
}