package com.example.desafiopebmed.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.desafiopebmed.R
import com.example.desafiopebmed.viewmodel.MedicalListViewModel
import com.example.desafiopebmed.viewmodel.utils.ViewData
import androidx.lifecycle.Observer

class MainActivity : BaseActivity() {

    private val medicalListViewModel: MedicalListViewModel by viewModels { viewModelFactory }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(medicalListViewModel) {
            lifecycle.addObserver(this)
            observePageContent(this)
        }
        medicalListViewModel.loadMedicalList()
    }

    override fun layoutResource(): Int = R.layout.activity_main

    override fun setupView() {
        
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