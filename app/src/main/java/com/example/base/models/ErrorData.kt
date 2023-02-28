package com.example.base.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorData(
    val text: String,
    val code: Int
)