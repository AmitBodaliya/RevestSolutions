package com.abapp.revestsolutions.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.abapp.revestsolutions.LogD
import com.abapp.revestsolutions.api.ProductRepositoryImpl
import com.abapp.revestsolutions.model.Product
import com.abapp.revestsolutions.ui.state.ProductUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch




@OptIn(FlowPreview::class)
class ProductViewModel(
    private val repository: ProductRepositoryImpl
) : ScreenModel {




    //ui state
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Idle)
    val uiState: StateFlow<ProductUiState> = _uiState

    //list products
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    //search result
    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> = _searchResults

    //filter list
    private val _filterResults = MutableStateFlow<List<Product>>(emptyList())
    val filterResults: StateFlow<List<Product>> = _filterResults



    private val _searchQuery = MutableStateFlow("")




    //filter
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory




    init {
        //load product
        loadProducts()

        //load category
        loadCategories()


        //search
        screenModelScope.launch {
            _searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    searchProducts(query)
                }
        }
    }



    fun loadCategories() {
        _categories.value = listOf("smartphones", "fragrances", "skincare")
    }




    //update search text
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }



    //load list
    fun loadProducts() {
        screenModelScope.launch {
            _uiState.value = ProductUiState.Loading

            try {
                val products = repository.getProducts()

                LogD("TAG", "products $products" )

                //set
                _uiState.value = ProductUiState.Success
                _products.value = products

            } catch (e: Exception) {
                _uiState.value = ProductUiState.Error(e.message)
            }
        }
    }



    //by search
    fun searchProducts(query: String) {
        screenModelScope.launch {
            _uiState.value = ProductUiState.Loading

            try {
                val products = repository.searchProducts(query)

                //set
                _uiState.value = ProductUiState.Success
                _searchResults.value = products

            } catch (e: Exception) {
                _uiState.value = ProductUiState.Error(e.message)
            }
        }
    }



    //on select
    fun onCategorySelected(category: String?) {
        _selectedCategory.value = category

        screenModelScope.launch {
            if (category.isNullOrEmpty()) {
                loadProducts()
            } else {
                _uiState.value = ProductUiState.Loading
                try {
                    val filtered = repository.getProductsByCategory(category)
                    _filterResults.value = filtered

                    //update ui
                    _uiState.value = if (filtered.isEmpty()) {
                        ProductUiState.Empty
                    } else {
                        ProductUiState.Success
                    }

                } catch (e: Exception) {
                    _uiState.value = ProductUiState.Error(e.message ?: "Failed to load category")
                }
            }
        }
    }


}


