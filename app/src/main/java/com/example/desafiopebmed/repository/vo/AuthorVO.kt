package com.example.desafiopebmed.repository.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthorVO(
    val name: String? = null
) : Parcelable