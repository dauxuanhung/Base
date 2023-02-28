package com.example.base.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView

@SuppressLint("ClickableViewAccessibility")
fun View.doOnTouchView(doSomething: () -> Unit) {
    setOnTouchListener { _, _ ->
        doSomething()
        false
    }
}

fun TextView.showWithContent(content: String?) {
    if (!content.isNullOrBlank()) {
        text = content
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}

fun View.changeVisibilityStateView() {
    if (this.visibility == View.GONE) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.changeVisibility(visibility: Boolean) {
    if (visibility) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.changeInVisibility(visibility: Boolean) {
    if (visibility) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}
