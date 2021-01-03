package com.example.desafiopebmed.ui.details

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.desafiopebmed.databinding.ActivityDetailsBinding
import com.example.desafiopebmed.repository.vo.ContentVO
import com.example.desafiopebmed.ui.OnRecyclerViewListener
import dagger.android.support.DaggerAppCompatActivity

class DetailsActivity : DaggerAppCompatActivity(), OnRecyclerViewListener.OnItemClickListener {

    companion object{
        const val CONTENT_EXTRA = "CONTENT_EXTRA"
    }
    private lateinit var binding: ActivityDetailsBinding
    private var contentVO : ContentVO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        handleIntent()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.collapsingToolbar.title = contentVO?.name
        binding.detailsTitle.text = contentVO?.description
        Glide.with(this).load(contentVO?.urlImage).into(binding.image)
    }

    private fun handleIntent() {
        contentVO = intent.extras?.get(CONTENT_EXTRA) as ContentVO?
    }
}