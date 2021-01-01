package com.example.desafiopebmed.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), View.OnClickListener {
    
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutResource()?.run { setContentView(this) }
        setupView()
    }

    override fun onBackPressed() {
        finish()
    }

    abstract fun layoutResource(): Int?

    abstract fun setupView()

    override fun onClick(view: View?) {}
}