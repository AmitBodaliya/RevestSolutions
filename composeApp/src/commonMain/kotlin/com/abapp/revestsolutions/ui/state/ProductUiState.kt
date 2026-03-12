package com.abapp.revestsolutions.ui.state



sealed class ProductUiState {
    object Idle : ProductUiState()
    object Loading : ProductUiState()
    object Success : ProductUiState()
    object Empty : ProductUiState()
    data class Error(val message: String?) : ProductUiState()
}