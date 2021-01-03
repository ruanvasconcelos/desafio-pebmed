package com.example.desafiopebmed.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiopebmed.databinding.HomeThumbViewHolderBinding
import com.example.desafiopebmed.databinding.HomeTitleViewHolderBinding
import com.example.desafiopebmed.repository.vo.ComponentType
import com.example.desafiopebmed.repository.vo.ItemVO
import com.example.desafiopebmed.ui.OnRecyclerViewListener

class HomeAdapter(
    private val onItemClickListener: OnRecyclerViewListener.OnItemClickListener,
) :
    ListAdapter<ItemVO, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<ItemVO>() {
        override fun areItemsTheSame(oldItem: ItemVO, newItem: ItemVO) =
            oldItem.category?.name == newItem.category?.name

        override fun areContentsTheSame(oldItem: ItemVO, newItem: ItemVO) =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ComponentType.HEADER_TITLE.ordinal -> {
                val binding = HomeTitleViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeTitleViewHolder(binding)
            }

            else -> {
                val binding = HomeThumbViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeThumbViewHolder(binding, onItemClickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemVO = currentList[position]

        when (getItemViewType(position)) {
            ComponentType.HEADER_TITLE.ordinal -> (holder as? HomeTitleViewHolder)?.bind(itemVO)

            else -> (holder as? HomeThumbViewHolder)?.bind(itemVO)
        }
    }

    override fun getItemViewType(position: Int): Int = currentList[position].componentType.ordinal

}