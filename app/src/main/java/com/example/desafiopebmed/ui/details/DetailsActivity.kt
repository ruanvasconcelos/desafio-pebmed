package com.example.desafiopebmed.ui.details

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.desafiopebmed.databinding.ActivityDetailsBinding
import com.example.desafiopebmed.repository.vo.AuthorVO
import com.example.desafiopebmed.ui.OnRecyclerViewListener
import com.example.desafiopebmed.viewmodel.DetailsViewModel
import com.example.desafiopebmed.viewmodel.utils.ViewData
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject

class DetailsActivity : DaggerAppCompatActivity(), OnRecyclerViewListener.OnItemClickListener {

    companion object {
        const val CONTENT_ID_EXTRA = "CONTENT_ID_EXTRA"
        const val LINE_BREAK = "\n"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val detailsViewModel: DetailsViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityDetailsBinding
    private var contentId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntent()
        with(detailsViewModel) {
            lifecycle.addObserver(this)
            observePageContent(this)
        }
        detailsViewModel.loadContentDetails(contentId)

        setupView()
    }

    private fun handleIntent() {
        contentId = intent.getIntExtra(CONTENT_ID_EXTRA, 0)
    }

    private fun setupView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
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

    private fun observePageContent(detailsViewModel: DetailsViewModel) =
        detailsViewModel.liveDataContentDetails.observe(this,
            {
                when (it?.status) {
                    ViewData.Status.LOADING -> {
                    }

                    ViewData.Status.COMPLETE -> {
                        val contentVO = it.data
                        Glide.with(this).load(contentVO?.urlImage).into(binding.image)
                        binding.collapsingToolbar.title = contentVO?.name
                        binding.detailsDescriptionContent.text = contentVO?.description
                        binding.detailsAuthorsContent.text = buildAuthorList(contentVO?.authors)
                    }

                    ViewData.Status.ERROR -> {
                    }
                }
            })

    private fun buildAuthorList(authors: List<AuthorVO>?): String {
        var authorsText = ""
        authors?.forEach { person ->
            authorsText =
                authorsText.plus(person.name?.capitalize(Locale.getDefault())).plus(LINE_BREAK)
        }
        return authorsText
    }
}