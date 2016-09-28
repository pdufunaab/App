package com.staaworks.util;

import android.widget.AbsListView;

/**
 * Created by Ahmad Alfawwaz on 9/1/2016
 */

public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {
    private int bufferItemCount;
    private int currentPage;
    private int itemCount;
    private boolean isLoading;


    protected InfiniteScrollListener() {
        bufferItemCount = 10;
        currentPage = 0;
        itemCount = 0;
        isLoading = true;
    }

    public abstract void loadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Do Nothing
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        if (totalItemCount < itemCount) {
            this.itemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.isLoading = true; }
        }

        if (isLoading && (totalItemCount > itemCount)) {
            isLoading = false;
            itemCount = totalItemCount;
            currentPage++;
        }

        if (!isLoading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + bufferItemCount)) {
            loadMore(currentPage + 1, totalItemCount);
            isLoading = true;
        }
    }
}