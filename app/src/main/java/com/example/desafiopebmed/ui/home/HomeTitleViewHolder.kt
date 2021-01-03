package com.example.desafiopebmed.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiopebmed.databinding.HomeTitleViewHolderBinding
import com.example.desafiopebmed.repository.vo.ItemVO
import com.example.desafiopebmed.ui.OnRecyclerViewListener

class HomeTitleViewHolder(
    itemView: HomeTitleViewHolderBinding,
    private val onItemClickListener: OnRecyclerViewListener.OnItemClickListener
) : RecyclerView.ViewHolder(itemView.root), View.OnClickListener {

    private var textView = itemView.title

    fun bind(data: ItemVO) {
        textView.text = data.category?.name
    }

    override fun onClick(view: View) {
        onItemClickListener.onItemClick(view, this@HomeTitleViewHolder.adapterPosition)
    }
}