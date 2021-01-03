package com.example.desafiopebmed.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafiopebmed.databinding.ActivityMainBinding
import com.example.desafiopebmed.viewmodel.MedicalListViewModel
import com.example.desafiopebmed.viewmodel.utils.ViewData
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), OnRecyclerViewListener.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityMainBinding
    private var medicalListAdapter = MedicalListAdapter(this)
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

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
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