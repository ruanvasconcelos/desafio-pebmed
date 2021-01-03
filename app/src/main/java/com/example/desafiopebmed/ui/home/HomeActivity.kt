package com.example.desafiopebmed.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafiopebmed.databinding.ActivityHomeBinding
import com.example.desafiopebmed.repository.vo.ComponentType
import com.example.desafiopebmed.ui.OnRecyclerViewListener
import com.example.desafiopebmed.ui.gone
import com.example.desafiopebmed.ui.visible
import com.example.desafiopebmed.viewmodel.MedicalListViewModel
import com.example.desafiopebmed.viewmodel.utils.ViewData
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), OnRecyclerViewListener.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityHomeBinding
    private var medicalListAdapter = HomeAdapter(this)
    private val medicalListViewModel: MedicalListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(medicalListViewModel) {
            lifecycle.addObserver(this)
            observePageContent(this)
        }
        medicalListViewModel.loadMedicalList()

        setupView()
    }

    private fun setupView() {
        val gridlayoutManager = GridLayoutManager(this@HomeActivity, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position < medicalListAdapter.currentList.size
                        && medicalListAdapter.getItemViewType(position) == ComponentType.GRID_THUMB.ordinal
                    ) {
                        1
                    } else spanCount
                }
            }
        }

        binding.recyclerView.apply {
            layoutManager = gridlayoutManager
            adapter = medicalListAdapter
        }
    }

    private fun observePageContent(medicalListViewModel: MedicalListViewModel) =
        medicalListViewModel.liveDataMedicalList.observe(this,
            {
                when (it?.status) {
                    ViewData.Status.LOADING -> {
                        binding.progressBar.visible()
                        binding.recyclerView.gone()
                        // Esconder View de erro
                        // Esconder View de empty state
                    }

                    ViewData.Status.COMPLETE -> {
                        binding.progressBar.gone()
                        medicalListAdapter.submitList(it.data)
                        binding.recyclerView.visible()
                        // Caso a lista esteja vazia, apresentar empty state
                    }

                    ViewData.Status.ERROR -> {
                        binding.progressBar.gone()
                        // Apresentar view de erro
                    }
                }
            })

    override fun onItemClick(view: View, position: Int) {
        // chamar detalhes
    }
}