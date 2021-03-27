package com.example.bestsecret.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bestsecret.R
import com.example.bestsecret.domain.state.DataState
import com.example.bestsecret.ext.hideInProgress
import com.example.bestsecret.ext.showError
import com.example.bestsecret.ext.showInProgress
import com.example.bestsecret.ui.viewmodel.MainStateEvent
import com.example.bestsecret.ui.viewmodel.ProductsViewModel
import com.example.bestsecret.ui.adapter.ProductsAdapter
import com.example.bestsecret.ui.dummy.DummyProducts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products.*

private val TAG =  ProductsFragment::class.java.simpleName

/**
 * A fragment representing a list of products.
 */
@AndroidEntryPoint
class ProductsFragment : Fragment() {


    private val viewModel: ProductsViewModel by viewModels()
    private val productsAdapter = ProductsAdapter()

    // region LIFECYCLE ----------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
        setObserver()

        viewModel.setStateEvent(MainStateEvent.GetAllProductsEvent)
    }
    // endregion

    // region PRIVATE METHODS -----------------------------------------------------------------------
    private fun setLayout() {
        product_recycler_view.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = productsAdapter
        }
    }

    private fun setObserver() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success -> {
                    val productList = dataState.data
                    Log.e(TAG, "Success data: $productList")
                    hideInProgress()
                    if (productList != null) {
                        productsAdapter.setProducts(productList)
                    } else {
                        productsAdapter.setProducts(DummyProducts.ITEMS)
                    }
                }
                is DataState.Failure -> {
                    Log.e(TAG, "Failure", dataState.error)
                    hideInProgress()
                    showError(dataState.error)
                    productsAdapter.setProducts(DummyProducts.ITEMS)
                }
                is DataState.InProgress -> {
                    Log.e(TAG, "In progress")
                    showInProgress()
                }
            }

        })
    }
    // endregion
}