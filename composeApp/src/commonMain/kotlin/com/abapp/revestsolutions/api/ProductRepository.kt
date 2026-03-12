package com.abapp.revestsolutions.api

import com.abapp.revestsolutions.model.Product



interface ProductRepository {

    suspend fun getProducts(): List<Product>

    suspend fun searchProducts(query: String): List<Product>

    suspend fun getProductsByCategory(category: String): List<Product>

}