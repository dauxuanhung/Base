package com.example.base.base

data class BaseListState<T>(val data: List<T> = listOf(), val endList: Boolean = false, val emptyList: Boolean = false)