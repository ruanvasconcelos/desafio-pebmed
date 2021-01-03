package com.example.desafiopebmed.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.desafiopebmed.databinding.ActivityDetailsBinding
import com.example.desafiopebmed.ui.OnRecyclerViewListener
import com.example.desafiopebmed.viewmodel.MedicalListViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailsActivity : DaggerAppCompatActivity(), OnRecyclerViewListener.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityDetailsBinding
    private val medicalListViewModel: MedicalListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        with(medicalListViewModel) {
//            lifecycle.addObserver(this)
//            observePageContent(this)
//        }
//        medicalListViewModel.loadMedicalList()

        setupView()
    }

    private fun setupView() {

    }

//    private fun observePageContent(medicalListViewModel: MedicalListViewModel) =
//        medicalListViewModel.liveDataMedicalList.observe(this,
//            {
//                when (it?.status) {
//                    ViewData.Status.LOADING -> {
//                        binding.progressBar.visible()
//                        binding.recyclerView.gone()
//                        // Esconder View de erro
//                        // Esconder View de empty state
//                    }
//
//                    ViewData.Status.COMPLETE -> {
//                        binding.progressBar.gone()
//                        binding.recyclerView.visible()
//                        // Caso a lista esteja vazia, apresentar empty state
//                    }
//
//                    ViewData.Status.ERROR -> {
//                        binding.progressBar.gone()
//                        // Apresentar view de erro
//                    }
//                }
//            })
}