package com.example.desafiopebmed.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiopebmed.databinding.HomeThumbViewHolderBinding
import com.example.desafiopebmed.repository.vo.ItemVO
import com.example.desafiopebmed.ui.OnRecyclerViewListener

class HomeThumbViewHolder(
    itemView: HomeThumbViewHolderBinding,
    private val onItemClickListener: OnRecyclerViewListener.OnItemClickListener
) : RecyclerView.ViewHolder(itemView.root), View.OnClickListener {

    private var imageView = itemView.contentImage
    private var textView = itemView.contentTitle

    init {
        itemView.cardView.setOnClickListener(this@HomeThumbViewHolder)
    }

    fun bind(data: ItemVO) {

        data.content?.let { content ->
            Glide.with(itemView.context).load(content.urlImage).into(imageView)
            textView.text = content.name
        }
    }

    override fun onClick(view: View) {
        onItemClickListener.onItemClick(view, this@HomeThumbViewHolder.adapterPosition)
    }
}