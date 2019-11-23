package com.example.fitnews

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val title: String? = null,
    val link: String? = null,
    val pubDate: String? = null,
    val description: String? = null
): Parcelable