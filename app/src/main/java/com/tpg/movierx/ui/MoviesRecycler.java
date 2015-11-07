package com.tpg.movierx.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by karoly.szanto on 18/07/15.
 */
public class MoviesRecycler extends RecyclerView {

    private View emptyView;

    public MoviesRecycler(Context context) {
        super(context);
    }

    public MoviesRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoviesRecycler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            handleChange();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            handleChange();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            handleChange();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            handleChange();
        }

        @Override
        public void onChanged() {
            handleChange();
        }

        private void handleChange() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    MoviesRecycler.this.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    MoviesRecycler.this.setVisibility(View.VISIBLE);
                }
            }
        }
    };

}
