package com.example.desafiopebmed.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafiopebmed.databinding.ActivityMainBinding
import com.example.desafiopebmed.repository.vo.ComponentType
import com.example.desafiopebmed.ui.OnRecyclerViewListener
import com.example.desafiopebmed.viewmodel.MedicalListViewModel
import com.example.desafiopebmed.viewmodel.utils.ViewData
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), OnRecyclerViewListener.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityMainBinding
    private var medicalListAdapter = HomeAdapter(this)
    private val medicalListViewModel: MedicalListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
                if (it?.status == ViewData.Status.COMPLETE) {
                    medicalListAdapter.submitList(it.data)
                }
            })

    override fun onItemClick(view: View, position: Int) {
        // chamar detalhes
    }
}