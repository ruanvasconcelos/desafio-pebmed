package com.example.desafiopebmed.ui

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiopebmed.databinding.ViewHolderMedicalBinding
import com.example.desafiopebmed.repository.vo.RootVO

class MedicalListViewHolder(
    itemView: ViewHolderMedicalBinding,
    private val onItemClickListener: OnRecyclerViewListener.OnItemClickListener
) : RecyclerView.ViewHolder(itemView.root), View.OnClickListener {

    private var imageView = itemView.contentImage
    private var textView = itemView.contentTitle

    init {
        itemView.cardView.setOnClickListener(this@MedicalListViewHolder)
    }

    fun bind(data: RootVO) {
        imageView.setBackgroundColor(Color.BLUE)
        textView.text = data.content?.name
    }

    override fun onClick(view: View) {
//        onItemClickListener.onItemClick(view, bindingAdapterPosition)
    }
}