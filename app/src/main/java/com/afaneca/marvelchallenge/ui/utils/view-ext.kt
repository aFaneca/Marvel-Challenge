package com.afaneca.marvelchallenge.ui.utils

import android.view.View
import android.view.ViewGroup
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager

/**
 * Hides/Displays selected view with a [transition] animation of [duration] ms
 */
fun View.setVisibilityWithAnimation(
    parentView: ViewGroup,
    shouldBecomeVisible: Boolean,
    transition: Transition = Fade(),
    duration: Long = 500L
) {
    transition.duration = duration
    transition.addTarget(this)

    TransitionManager.beginDelayedTransition(parentView, transition)
    visibility = if (shouldBecomeVisible) View.VISIBLE else View.GONE
}