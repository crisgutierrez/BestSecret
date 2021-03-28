package com.example.bestsecret.ui.viewmodel

import androidx.lifecycle.*
import com.example.bestsecret.domain.model.Product
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
    private val _dataState: MutableLiveData<DataState<List<Product>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Product>>> = _dataState

    // region PUBLIC METHODS -----------------------------------------------------------------------
    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when(mainStateEvent) {
                is MainStateEvent.GetAllProductsEvent -> {
                    getAllProducts(1) // Todo remove hardcoded value when the BE is working and implement pagination.
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
                _dataState.value = it
            }.launchIn(viewModelScope)
    }
    // endregion

}

sealed class MainStateEvent {

    object GetAllProductsEvent: MainStateEvent()

}