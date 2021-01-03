package com.example.desafiopebmed.repository.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemVO(
    val category: CategoryVO? = null,
    val content: ContentVO? = null,
    val componentType: ComponentType? = ComponentType.UNKNOWN
) : Parcelable