package com.example.desafiopebmed.ui.details

import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.desafiopebmed.databinding.ActivityDetailsBinding
import com.example.desafiopebmed.repository.vo.AuthorVO
import com.example.desafiopebmed.repository.vo.ContentVO
import com.example.desafiopebmed.ui.OnRecyclerViewListener
import dagger.android.support.DaggerAppCompatActivity
import java.util.*

class DetailsActivity : DaggerAppCompatActivity(), OnRecyclerViewListener.OnItemClickListener {

    companion object{
        const val CONTENT_EXTRA = "CONTENT_EXTRA"
        const val LINE_BREAK = "\n"
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

        Glide.with(this).load(contentVO?.urlImage).into(binding.image)
        binding.collapsingToolbar.title = contentVO?.name

        binding.detailsDescriptionContent.text = contentVO?.description
        binding.detailsAuthorsContent.text = buildAuthorList(contentVO?.authors)

    }

    private fun buildAuthorList(authors: List<AuthorVO>?): String {
        var authorsText = ""
        authors?.forEach { person ->
            authorsText = authorsText.plus(person.name?.capitalize(Locale.getDefault())).plus(LINE_BREAK)
        }
        return authorsText
    }

    private fun handleIntent() {
        contentVO = intent.extras?.get(CONTENT_EXTRA) as ContentVO?
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}