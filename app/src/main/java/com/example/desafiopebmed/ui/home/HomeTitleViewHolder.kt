package com.example.desafiopebmed.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.example.desafiopebmed.databinding.HomeTitleViewHolderBinding
import com.example.desafiopebmed.repository.vo.ItemVO

class HomeTitleViewHolder(
    itemView: HomeTitleViewHolderBinding
) : RecyclerView.ViewHolder(itemView.root){

    private var textView = itemView.title

    fun bind(data: ItemVO) {
        textView.text = data.category?.name?.toUpperCase()
    }

}