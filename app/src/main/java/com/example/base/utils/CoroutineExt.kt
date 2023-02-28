package com.example.base.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

data class CoroutineCallBack(
    var block: suspend () -> Unit = {},
    var error: (Throwable) -> Unit = {}
)

fun CoroutineScope.safeLaunch(init: CoroutineCallBack.() -> Unit): Job {
    val callback = CoroutineCallBack().apply(init)
    return launch(CoroutineExceptionHandler { _, throwable ->
        callback.error.invoke(throwable)
    }) {
        callback.block.invoke()
    }
}
