package com.example.desafiopebmed.source.remote.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Content(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("urlImage") val urlImage: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("authors") val authors: List<Author>? = null
) : Parcelable