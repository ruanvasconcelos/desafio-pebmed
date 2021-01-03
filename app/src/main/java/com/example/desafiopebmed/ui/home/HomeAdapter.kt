package com.example.desafiopebmed.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.desafiopebmed.databinding.ViewHolderMedicalBinding
import com.example.desafiopebmed.repository.vo.ItemVO
import com.example.desafiopebmed.ui.OnRecyclerViewListener

class HomeAdapter(
    private val onItemClickListener: OnRecyclerViewListener.OnItemClickListener,
) :
    ListAdapter<ItemVO, HomeThumbViewHolder>(object :
        DiffUtil.ItemCallback<ItemVO>() {
        override fun areItemsTheSame(oldItem: ItemVO, newItem: ItemVO) =
            oldItem.category?.name == newItem.category?.name

        override fun areContentsTheSame(oldItem: ItemVO, newItem: ItemVO) =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeThumbViewHolder {
        val itemBinding = ViewHolderMedicalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeThumbViewHolder(itemBinding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: HomeThumbViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}