package com.example.desafiopebmed.source.local


data class ContentEntity(
    val contentId: Int? = null,
    val contentName: String? = null,
    val urlImage: String? = null,
    val description: String? = null,
    val authors: List<AuthorEntity>? = null
)