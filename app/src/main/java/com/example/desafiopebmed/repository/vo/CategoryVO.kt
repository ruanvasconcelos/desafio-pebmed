package com.example.desafiopebmed.repository.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryVO(
    val id: Int? = null,
    val name: String? = null
) : Parcelable