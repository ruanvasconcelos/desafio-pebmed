package com.example.desafiopebmed.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.desafiopebmed.databinding.ActivityMainBinding
import com.example.desafiopebmed.viewmodel.MedicalListViewModel
import com.example.desafiopebmed.viewmodel.utils.ViewData
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val medicalListViewModel: MedicalListViewModel by viewModels { viewModelFactory }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(medicalListViewModel) {
            lifecycle.addObserver(this)
            observePageContent(this)
        }
        medicalListViewModel.loadMedicalList()
    }

    private fun observePageContent(medicalListViewModel: MedicalListViewModel) =
        medicalListViewModel.liveDataMedicalList.observe(this,
            Observer {
                if (it?.status == ViewData.Status.COMPLETE) {
                    Toast.makeText(this, "Complete "+it.data?.size, Toast.LENGTH_SHORT).show()
                    //preencher tela
                }
            })
}