package com.example.desafiopebmed.repository.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentVO(
    val id: Int? = null,
    val name: String? = null,
    val urlImage: String? = null,
    val description: String? = null,
    val authors: List<AuthorVO>? = null
) : Parcelable