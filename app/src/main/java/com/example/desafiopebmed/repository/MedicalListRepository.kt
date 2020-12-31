package com.example.desafiopebmed.repository

import com.example.desafiopebmed.repository.vo.*
import com.example.desafiopebmed.source.remote.data.*
import com.example.desafiopebmed.source.remote.http.WebServiceManager
import io.reactivex.rxjava3.core.Observable


class MedicalListRepository {
    private val webServiceManager = WebServiceManager.instance

    fun recoverMedicalList(): Observable<List<RootVO>> =
        webServiceManager
            .webServiceAPI
            .getMedicalList()
            .map {
                transformToRootVOList(it)
            }

    private fun transformToRootVOList(roots: List<Root>?) =
        roots?.map {
            val category = it.category
            val content = it.content

            RootVO(
                transformToCategoryVO(category),
                transformToContentVO(content)
            )
        }

    private fun transformToContentVO(content: Content?) =
        ContentVO(
            content?.id,
            content?.name,
            content?.urlImage,
            content?.description,
            transformToAuthorVO(content?.authors)
        )

    private fun transformToAuthorVO(authors: List<Author>?): List<AuthorVO>? =
        authors?.map {
            AuthorVO(it.name)
        }

    private fun transformToCategoryVO(category: Category?) =
        CategoryVO(
            category?.id,
            category?.name
        )


}