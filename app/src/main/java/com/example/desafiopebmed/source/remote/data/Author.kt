package com.example.desafiopebmed.source.remote.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
    @SerializedName("name") val name: String? = null
) : Parcelable