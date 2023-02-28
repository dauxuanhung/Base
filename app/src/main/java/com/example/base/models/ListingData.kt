package com.example.base.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Listing(
    val id: Int,
    val images: Image,
    val address: Address
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Image(
    val image: String,
    @Json(name = "image_thumb") val imageThumb: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Address(val address1: String) : Parcelable
