package com.example.desafiopebmed.repository

import com.example.desafiopebmed.repository.vo.*
import com.example.desafiopebmed.source.remote.data.Author
import com.example.desafiopebmed.source.remote.data.Category
import com.example.desafiopebmed.source.remote.data.Content
import com.example.desafiopebmed.source.remote.data.Root
import com.example.desafiopebmed.source.remote.http.WebServiceAPI
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class MedicalListRepository @Inject constructor(private val webServiceAPI: WebServiceAPI) {

    fun recoverMedicalList(): Observable<List<ItemVO>> =
        webServiceAPI
            .getMedicalList()
            .map {
                transformToItemVOList(it)
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
    private fun transformToItemVOList(roots: List<Root>?): List<ItemVO> {
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