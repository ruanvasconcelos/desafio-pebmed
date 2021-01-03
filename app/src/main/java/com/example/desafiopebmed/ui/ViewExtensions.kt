package com.example.desafiopebmed.ui

import android.view.View

fun View.visible(): View {
    visibility = View.VISIBLE
    return this
}

fun View.gone(): View {
    visibility = View.GONE
    return this
}