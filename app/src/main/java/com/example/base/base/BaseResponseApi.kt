package com.example.base.base

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponseApi<T>(
    val data: T?,
    val success: Boolean,
    val code: Int
)