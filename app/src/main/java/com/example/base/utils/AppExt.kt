package com.example.base.utils

import android.content.Context
import android.util.TypedValue
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

inline fun Fragment.launchCoroutineWhenStarted(crossinline block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        block()
    }
}

inline fun LifecycleOwner.launchCoroutineWhenStarted(crossinline block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenStarted {
        block()
    }
}

fun Float.toIntPx(context: Context): Int {
    return toFloatPx(context).toInt()
}

fun Float.toFloatPx(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics
    )
}
