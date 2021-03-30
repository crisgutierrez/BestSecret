package com.example.bestsecret.ui.view

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bestsecret.R
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.state.DataState
import com.example.bestsecret.ext.*
import com.example.bestsecret.data.testsupport.DummyProducts
import com.example.bestsecret.ui.viewmodel.ProductDetailsStateEvent
import com.example.bestsecret.ui.viewmodel.ProductDetailsViewModel
import com.example.bestsecret.utils.formatNumberToTwoDigits
import com.example.bestsecret.utils.getPriceWithDiscount
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.product_details_fragment.*

private val TAG =  ProductDetailsFragment::class.java.simpleName

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductDetailsViewModel by viewModels()
    private val fragmentArgs: ProductDetailsFragmentArgs by navArgs()

    // region LIFECYCLE ----------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.product_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAppBar()
        setObserver()

        viewModel.setStateEvent(ProductDetailsStateEvent.GetProductByIdEvent(fragmentArgs.productId))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // endregion

    // region PRIVATE METHODS -----------------------------------------------------------------------
    private fun setAppBar() {
        (activity as MainActivity?)!!.supportActionBar.let {
            it?.title = getString(R.string.product_details_app_bar_title)
            it?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setObserver() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success -> {
                    val product = dataState.data
                    Log.d(TAG, "Success data: $product")
                    hideInProgress()
                    if (product != null) {
                        setLayout(product)
                    }
                }
                is DataState.Failure -> {
                    Log.e(TAG, "Failure", dataState.error)
                    hideInProgress()
                    handleError()
                }
                is DataState.InProgress -> {
                    Log.d(TAG, "In progress")
                    showInProgress()
                }
            }

        })
    }

    /**
     * Set the layout with the [product] data
     */
    private fun setLayout(product: Product) {
        product_details_image.loadFromUrl(product.image)
        product_details_name.text = product.name
        product_details_brand.text = product.brand
        product_details_description.text = product.description
        product_details_price.text = getString(R.string.product_item_price, getPriceWithDiscount(product.price, product.discountPercentage), product.currency)
        product_details_favorite.setOnClickListener { onFavoriteClicked() }

        checkDiscount(product)
        setAddToCartButton(product.stock)
    }

    /**
     * This method is to handle when the favorite is clicked, the idea is if we have support from the BE
     * to keep track of the favorite item of the user.
     */
    private fun onFavoriteClicked() {
        product_details_favorite.isSelected = !product_details_favorite.isSelected
    }

    /**
     * Set up add to cart button base on the [stock], if we don{t have stock we change the text to
     * Notify me when available if we have [stock] we set it as Add to cart.
     */
    private fun setAddToCartButton(stock: Int) {
        if (stock > 0) {
            product_details_add_cart_button.text = getString(R.string.product_details_add_cart_button)
        } else {
            product_details_add_cart_button.text = getString(R.string.product_details_notify_when_is_available_button)
        }

        product_details_add_cart_button.setOnClickListener { showDialog(getString(R.string.product_dialog_feature_not_implemented), getString(R.string.product_dialog_ok)) }
    }

    /**
     * Check the discount of the [product] and base on the discount we show or not the discount data
     * and if the discount is higher than [MIN_DISCOUNT_FOR_HOT_SALE] we show the deal stamp
     */
    private fun checkDiscount(product: Product) {
        product_details_old_price.isVisible = product.discountPercentage > 0
        product_details_old_price.text = getString(R.string.product_item_old_price, formatNumberToTwoDigits(product.price.toDouble()), product.currency)
        product_details_old_price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        product_details_discount.isVisible = product.discountPercentage > MIN_DISCOUNT_FOR_HOT_SALE
        product_details_discount.loadFromResources(R.drawable.deal_image)
    }

    /**
     * If there was an error loading the product detail we show an error dialog and if the user press
     * go back button we navigate back to the list of products.
     */
    private fun handleError() {
        showDialog(
                text = getString(R.string.product_details_dialog_error_message),
                buttonText = getString(R.string.product_details_dialog_go_back),
                isCancellable = false) {
            findNavController().popBackStack()
        }
    }
    // endregion

    companion object {
        private const val MIN_DISCOUNT_FOR_HOT_SALE = 30
    }
}