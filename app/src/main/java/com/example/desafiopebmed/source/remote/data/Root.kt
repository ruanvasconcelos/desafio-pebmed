package com.example.desafiopebmed.source.remote.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Root(
    @SerializedName("category") val category: Category? = null,
    @SerializedName("content") val content: Content? = null
) : Parcelable