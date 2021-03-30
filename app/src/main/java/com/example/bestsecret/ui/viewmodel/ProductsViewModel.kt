package com.example.bestsecret.ui.viewmodel

import androidx.lifecycle.*
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductList
import com.example.bestsecret.domain.model.ProductQueryParam
import com.example.bestsecret.domain.state.DataState
import com.example.bestsecret.domain.usecase.GetAllProductsUseCase
import com.example.bestsecret.utils.PRODUCTS_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAllProductsUseCase: GetAllProductsUseCase
): ViewModel(){
    private val _dataState: MutableLiveData<DataState<ProductList>> = MutableLiveData()
    val dataState: LiveData<DataState<ProductList>> = _dataState
    private var productList: ProductList? = null
    var productPageCount = 1

    // region PUBLIC METHODS -----------------------------------------------------------------------
    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when(mainStateEvent) {
                is MainStateEvent.GetAllProductsEvent -> {
                    getAllProducts(productPageCount)
                }
            }
        }
    }
    // endregion

    // region PRIVATE METHODS -----------------------------------------------------------------------
    /**
     * Get all the products for the selected [page]
     */
    private suspend fun getAllProducts(page: Int) {
        getAllProductsUseCase.getProducts(ProductQueryParam.QueryAllProducts(page, PRODUCTS_PAGE_SIZE))
            .onEach {
                val response = it.peekDataOrNull()
                if (response != null) {
                    productPageCount++
                    if (productList == null) {
                        productList = response
                    } else {
                        productList!!.list.addAll(response.list)
                    }
                    _dataState.value = DataState.Success(productList)
                } else {
                    _dataState.value = it
                }
            }.launchIn(viewModelScope)
    }
    // endregion

}

sealed class MainStateEvent {

    object GetAllProductsEvent: MainStateEvent()

}