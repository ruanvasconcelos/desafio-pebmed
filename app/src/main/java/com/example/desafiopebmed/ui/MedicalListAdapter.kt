package com.example.desafiopebmed.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.desafiopebmed.databinding.ViewHolderMedicalBinding
import com.example.desafiopebmed.repository.vo.RootVO

class MedicalListAdapter(
    private val onItemClickListener: OnRecyclerViewListener.OnItemClickListener,
) :
    ListAdapter<RootVO, MedicalListViewHolder>(object :
        DiffUtil.ItemCallback<RootVO>() {
        override fun areItemsTheSame(oldItem: RootVO, newItem: RootVO) =
            oldItem.category?.name == newItem.category?.name

        override fun areContentsTheSame(oldItem: RootVO, newItem: RootVO) =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalListViewHolder {
        val itemBinding = ViewHolderMedicalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicalListViewHolder(itemBinding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MedicalListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}