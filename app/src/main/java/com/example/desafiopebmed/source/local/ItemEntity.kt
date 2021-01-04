package com.example.desafiopebmed.source.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.desafiopebmed.repository.vo.ComponentType

@Entity(tableName = "medicalList")
data class ItemEntity(
    @PrimaryKey
    var uid: Int,
    @Embedded
    val category: CategoryEntity? = null,
    @Embedded
    val content: ContentEntity? = null,
    val componentType: ComponentType = ComponentType.UNKNOWN
)