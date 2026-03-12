package com.abapp.revestsolutions.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val products: List<Product>
)