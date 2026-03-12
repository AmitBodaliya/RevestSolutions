package com.abapp.revestsolutions.api

import com.abapp.revestsolutions.model.Product



class ProductRepositoryImpl(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return api.getProducts()
    }

    override suspend fun searchProducts(query: String): List<Product> {
        return api.searchProducts(query)
    }

    override suspend fun getProductsByCategory(category: String): List<Product> {
        return api.getProductsByCategory(category)
    }

}