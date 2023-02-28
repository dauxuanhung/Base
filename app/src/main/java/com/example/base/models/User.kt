package com.example.base.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val token: String,
    @Json(name = "full_name") val fullName: String,
    val email: String,
    val phone: String,
    val avatar: String
) : Parcelable