package com.example.desafiopebmed.ui.details

import android.os.Bundle
import com.example.desafiopebmed.databinding.ActivityDetailsBinding
import com.example.desafiopebmed.ui.OnRecyclerViewListener
import dagger.android.support.DaggerAppCompatActivity

class DetailsActivity : DaggerAppCompatActivity(), OnRecyclerViewListener.OnItemClickListener {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.collapsingToolbar.title = "Detalhes"
    }
}