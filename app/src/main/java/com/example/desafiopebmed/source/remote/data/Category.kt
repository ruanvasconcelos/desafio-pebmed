package com.example.desafiopebmed.source.remote.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null
) : Parcelable