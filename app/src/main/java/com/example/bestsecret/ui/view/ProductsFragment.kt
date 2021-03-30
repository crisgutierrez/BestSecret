package com.example.bestsecret.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bestsecret.R
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.state.DataState
import com.example.bestsecret.ext.hideInProgress
import com.example.bestsecret.ext.showDialog
import com.example.bestsecret.ext.showError
import com.example.bestsecret.ext.showInProgress
import com.example.bestsecret.ui.adapter.ProductsAdapter
import com.example.bestsecret.ui.viewmodel.MainStateEvent
import com.example.bestsecret.ui.viewmodel.ProductsViewModel
import com.example.bestsecret.utils.PRODUCTS_PAGE_SIZE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products.*

private val TAG =  ProductsFragment::class.java.simpleName

/**
 * A fragment representing a list of products.
 */
@AndroidEntryPoint
class ProductsFragment : Fragment() {


    private val viewModel: ProductsViewModel by viewModels()
    private val productsAdapter = ProductsAdapter(onProductClicked(), onAddToCartClicked())

    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false

    /**
     * Product clicked listener
     */
    private fun onProductClicked(): (Product) -> Unit = { product ->
        findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment().setProductId(product.id))
    }

    /**
     * Add to cart listener
     */
    private fun onAddToCartClicked(): () -> Unit = { showDialog(getString(R.string.product_dialog_feature_not_implemented), getString(R.string.product_dialog_ok)) }

    /**
     * Create scrollListener to handle pagination.
     */
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            // Gather data of the layoutManager
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            // Validations
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = (firstVisibleItemPosition + visibleItemCount) >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= PRODUCTS_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.setStateEvent(MainStateEvent.GetAllProductsEvent)
                isScrolling = false
            }
        }
    }

    // region LIFECYCLE ----------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

        if (!isLastPage) {
            viewModel.setStateEvent(MainStateEvent.GetAllProductsEvent)
        }
    }
    // endregion

    // region PRIVATE METHODS -----------------------------------------------------------------------
    private fun setLayout() {
        setAppBar()

        product_recycler_view.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = productsAdapter
            addOnScrollListener(this@ProductsFragment.scrollListener)
        }
    }

    private fun setAppBar() {
        (activity as? MainActivity?)?.supportActionBar.let {
            it?.title = getString(R.string.product_app_bar_title)
            it?.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setObserver() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success -> {
                    hideInProgress()
                    val productList = dataState.data
                    Log.d(TAG, "Success data: $productList")
                    isLoading = false
                    if (productList != null) {
                        productsAdapter.setProducts(productList.list)
                        isLastPage = (productList.size / PRODUCTS_PAGE_SIZE) + 1 == viewModel.productPageCount
                    } else {
                        Log.e(TAG, "productList is null")
                    }
                }
                is DataState.Failure -> {
                    hideInProgress()
                    Log.e(TAG, "Failure", dataState.error)
                    isLoading = false
                    showError(dataState.error)
                }
                is DataState.InProgress -> {
                    showInProgress()
                    Log.d(TAG, "In progress")
                    isLoading = true
                }
            }

        })
    }
    // endregion
}