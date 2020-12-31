package com.example.desafiopebmed.repository.vo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RootVO(
    @SerializedName("category") val category: CategoryVO? = null,
    @SerializedName("content") val content: ContentVO? = null
) : Parcelable