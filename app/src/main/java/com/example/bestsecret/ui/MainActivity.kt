package com.example.bestsecret.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bestsecret.R
import com.example.bestsecret.domain.state.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        setObserver()

        // Todo remove this call when we have the correct ui implementation
        viewModel.setStateEvent(MainStateEvent.GetAllProductsEvent)
        viewModel.setStateEvent(MainStateEvent.GetProductByEvent)
    }

    fun setObserver() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when(dataState){
                is DataState.Success -> {
                    Log.e("cristian", "Success data: ${dataState.data}")

                }
                is DataState.Failure -> {
                    Log.e("cristian", "Failure", dataState.error)
                }
                is DataState.InProgress -> {
                    Log.e("cristian", "In progress")
                }
            }

        })
    }
}