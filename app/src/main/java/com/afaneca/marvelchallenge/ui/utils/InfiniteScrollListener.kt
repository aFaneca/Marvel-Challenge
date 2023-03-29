package com.afaneca.marvelchallenge.ui.utils

import androidx.recyclerview.widget.RecyclerView

/**
 * Basic infinite scroll listener.
 * The method [onLoadMore] is called whenever the recycler view has reached the bottom
 * and is ready for more items if they exist.
 */
class InfiniteScrollListener(
    val onLoadMore: (view: RecyclerView) -> Unit
) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!recyclerView.canScrollVertically(1) && dy > 0) {
            //scrolled to BOTTOM
            onLoadMore(recyclerView)
        }
    }
}
