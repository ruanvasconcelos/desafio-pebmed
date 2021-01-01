package com.example.desafiopebmed.repository

import com.example.desafiopebmed.repository.vo.AuthorVO
import com.example.desafiopebmed.repository.vo.CategoryVO
import com.example.desafiopebmed.repository.vo.ContentVO
import com.example.desafiopebmed.repository.vo.RootVO
import com.example.desafiopebmed.source.remote.data.Author
import com.example.desafiopebmed.source.remote.data.Category
import com.example.desafiopebmed.source.remote.data.Content
import com.example.desafiopebmed.source.remote.data.Root
import com.example.desafiopebmed.source.remote.http.WebServiceAPI
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class MedicalListRepository  @Inject constructor(private val webServiceAPI: WebServiceAPI) {

    fun recoverMedicalList(): Observable<List<RootVO>> =
        webServiceAPI
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