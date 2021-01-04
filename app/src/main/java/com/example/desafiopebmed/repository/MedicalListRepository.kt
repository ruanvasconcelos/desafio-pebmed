package com.example.desafiopebmed.repository

import com.example.desafiopebmed.repository.vo.*
import com.example.desafiopebmed.source.local.*
import com.example.desafiopebmed.source.remote.data.Author
import com.example.desafiopebmed.source.remote.data.Category
import com.example.desafiopebmed.source.remote.data.Content
import com.example.desafiopebmed.source.remote.data.Root
import com.example.desafiopebmed.source.remote.http.WebServiceAPI
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class MedicalListRepository @Inject constructor(
    private val webServiceAPI: WebServiceAPI,
    private val database: Database,
) {

    fun loadMedicalList(): Observable<List<ItemVO>> = webServiceAPI
        .getMedicalList()
        .map { rootList ->
            val itemVOList = extractCategoryAsItemAndTransformToItemVOList(rootList)
            if (itemVOList.isNotEmpty()) {
                saveLocalItemList(itemVOList)
            }
            itemVOList
        }
        .onErrorResumeNext {
            recoverLocalItemList()
        }

    internal fun saveLocalItemList(itemVOList: List<ItemVO>) {
        database.itemListDao().insertAll(
            transformItemVOListToItemEntityList(itemVOList)
        )
    }

    internal fun recoverLocalItemList(): Observable<List<ItemVO>> = database
        .itemListDao()
        .getAll()
        .subscribeOn(Schedulers.io())
        .toObservable()
        .map { itemEntityList ->
            return@map transformItemEntityListToItemVOList(
                itemEntityList
            )
        }
        .onTerminateDetach()
        .onErrorResumeNext { Observable.defer { Observable.just(emptyList()) } }

    private fun transformItemEntityListToItemVOList(itemEntityList: List<ItemEntity>?): List<ItemVO> =
        itemEntityList?.map { itemEntity ->
            ItemVO(
                category = CategoryVO(
                    itemEntity.category?.categoryId,
                    itemEntity.category?.categoryName
                ),
                content = ContentVO(
                    itemEntity.content?.contentId,
                    itemEntity.content?.contentName,
                    itemEntity.content?.urlImage,
                    itemEntity.content?.description,
                    itemEntity.content?.authors?.map { authorEntity ->
                        AuthorVO(authorEntity.authorName)
                    }
                ),
                componentType = itemEntity.componentType
            )
        } ?: emptyList()

    private fun transformItemVOListToItemEntityList(itemVOList: List<ItemVO>): List<ItemEntity> =
        itemVOList.mapIndexed { index, itemVO ->
            ItemEntity(
                uid = index,
                category = CategoryEntity(
                    categoryId = itemVO.category?.id,
                    categoryName = itemVO.category?.name
                ),
                content = ContentEntity(
                    contentId = itemVO.content?.id,
                    contentName = itemVO.content?.name,
                    urlImage = itemVO.content?.urlImage,
                    description = itemVO.content?.description,
                    authors = itemVO.content?.authors?.map {
                        AuthorEntity(it.name)
                    }
                ),
                componentType = itemVO.componentType
            )
        }

    /**
     * Transforma o objeto de retorno do backend numa lista onde as categorias e demais itens
     * estão separados de forma clara.
     *
     * As categorias são adicionadas na nova lista com o type ComponentType.HEADER_TITLE
     * Os demais itens são adicionados na nova lista com o type ComponentType.GRID_THUMB
     *
     * @param itemVOList Lista de itens que podem ser do tipo Título de agrupador ou Thumb do Grid
     * @param category Categoria retornada do backend
     */
    internal fun extractCategoryAsItemAndTransformToItemVOList(roots: List<Root>?): List<ItemVO> {
        val itemVOList = ArrayList<ItemVO>()

        roots?.map { root ->
            if (isNewCategory(itemVOList, root.category)) {
                itemVOList.add(
                    ItemVO(
                        category = transformToCategoryVO(root.category),
                        componentType = ComponentType.HEADER_TITLE
                    )
                )
            }

            itemVOList.add(
                ItemVO(
                    content = transformToContentVO(root.content),
                    componentType = ComponentType.GRID_THUMB
                )
            )
        }
        return itemVOList
    }

    /**
     * Verifica se a categoria em questão existe dentro da lista de Itens.
     *
     * @param itemVOList Lista de itens que podem ser do tipo Título de agrupador ou Thumb do Grid
     * @param category Categoria retornada do backend
     */
    private fun isNewCategory(itemVOList: ArrayList<ItemVO>, category: Category?): Boolean {
        val categoryWeLookFor = itemVOList.find {
            it.componentType == ComponentType.HEADER_TITLE
                    && it.category?.id == category?.id
        }
        return categoryWeLookFor == null
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