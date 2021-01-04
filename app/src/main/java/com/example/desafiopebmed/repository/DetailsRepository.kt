package com.example.desafiopebmed.repository

import com.example.desafiopebmed.repository.vo.AuthorVO
import com.example.desafiopebmed.repository.vo.ContentVO
import com.example.desafiopebmed.source.local.Database
import com.example.desafiopebmed.source.local.ItemEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class DetailsRepository @Inject constructor(
    private val database: Database
) {

    fun loadContent(id: Int?): Observable<ContentVO> = database
        .itemListDao()
        .itemByContentId(id)
        .subscribeOn(Schedulers.io())
        .toObservable()
        .map { itemEntity ->
            transformToContentVO(itemEntity)
        }

    private fun transformToContentVO(itemEntity: ItemEntity) =
        ContentVO(
            itemEntity.content?.contentId,
            itemEntity.content?.contentName,
            itemEntity.content?.urlImage,
            itemEntity.content?.description,
            itemEntity.content?.authors?.map { authorEntity ->
                AuthorVO(authorEntity.authorName)
            }
        )
}