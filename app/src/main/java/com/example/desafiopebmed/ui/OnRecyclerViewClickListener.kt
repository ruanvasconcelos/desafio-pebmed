package com.example.desafiopebmed.ui

import android.view.View

interface OnRecyclerViewListener {

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int) {}
    }
}