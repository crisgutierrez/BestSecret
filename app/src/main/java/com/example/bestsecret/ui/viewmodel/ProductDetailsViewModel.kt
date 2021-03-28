package com.example.bestsecret.ui.viewmodel

import androidx.lifecycle.*
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductQueryParam
import com.example.bestsecret.domain.state.DataState
import com.example.bestsecret.domain.usecase.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel
@Inject
constructor(
        private val savedStateHandle: SavedStateHandle,
        private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<Product>> = MutableLiveData()
    val dataState: LiveData<DataState<Product>> = _dataState

    // region PUBLIC METHODS -----------------------------------------------------------------------
    fun setStateEvent(stateEvent: ProductDetailsStateEvent) {
        viewModelScope.launch {
            when(stateEvent) {
                is ProductDetailsStateEvent.GetProductByIdEvent -> { getProductById(stateEvent.productId) }
            }
        }
    }
    // endregion

    // region PRIVATE METHODS -----------------------------------------------------------------------
    /**
     * Get product using the [productId]
     */
    private suspend fun getProductById(productId: Int) {
        getProductByIdUseCase.getProductById(ProductQueryParam.QueryProductById(productId))
            .onEach {
                _dataState.value = it
            }.launchIn(viewModelScope)
    }
    // endregion

}

sealed class ProductDetailsStateEvent {

    class GetProductByIdEvent(val productId: Int): ProductDetailsStateEvent()

}