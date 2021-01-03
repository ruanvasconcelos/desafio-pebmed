package com.example.desafiopebmed.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiopebmed.databinding.ViewHolderMedicalBinding
import com.example.desafiopebmed.repository.vo.ItemVO
import com.example.desafiopebmed.ui.OnRecyclerViewListener

class HomeThumbViewHolder(
    itemView: ViewHolderMedicalBinding,
    private val onItemClickListener: OnRecyclerViewListener.OnItemClickListener
) : RecyclerView.ViewHolder(itemView.root), View.OnClickListener {

    private var imageView = itemView.contentImage
    private var textView = itemView.contentTitle

    init {
        itemView.cardView.setOnClickListener(this@HomeThumbViewHolder)
    }

    fun bind(data: ItemVO) {
        Glide.with(itemView.context).load(data.content?.urlImage).into(imageView)
        textView.text = data.content?.name
    }

    override fun onClick(view: View) {
        onItemClickListener.onItemClick(view, this@HomeThumbViewHolder.adapterPosition)
    }
}