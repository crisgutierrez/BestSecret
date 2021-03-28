package com.example.bestsecret.ui.viewmodel

import androidx.lifecycle.*
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductQueryParam
import com.example.bestsecret.domain.state.DataState
import com.example.bestsecret.domain.usecase.GetAllProductsUseCase
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

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when(mainStateEvent) {
                is MainStateEvent.GetAllProductsEvent -> {
                    getAllProductsUseCase.getProducts(ProductQueryParam.QueryAllProducts(1,5))
                        .onEach {
                            _dataState.value = it
                        }.launchIn(viewModelScope)
                }
            }
        }
    }

}

sealed class MainStateEvent {

    object GetAllProductsEvent: MainStateEvent()

}