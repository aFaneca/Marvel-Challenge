package com.afaneca.marvelchallenge.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Simple item decorator for recycler view that adds a vertical [margin] between list items
 */
class VerticalMarginItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            top = margin
            bottom = margin
        }
    }
}