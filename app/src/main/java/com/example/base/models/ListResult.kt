package com.example.base.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListResult <T> (
    val total: Int,
    val data: List<T>
)