package com.abapp.revestsolutions.api

import com.abapp.revestsolutions.model.Product
import com.abapp.revestsolutions.model.ProductResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter



class ProductApi(
    private val client: HttpClient,
) {


    companion object {
        const val BASE_URL = "https://dummyjson.com"
    }




    suspend fun getProducts(): List<Product> {
        return try {
            val response: ProductResponse =
                client.get("$BASE_URL/products").body()

            response.products
        } catch (_: Exception) {
            emptyList()
        }
    }




    suspend fun searchProducts(query: String): List<Product> {
        return try {
            val response: ProductResponse =
                client.get("$BASE_URL/products/search") {
                    parameter("q", query)
                }.body()

            response.products
        } catch (_: Exception) {
            emptyList()
        }
    }





    suspend fun getProductsByCategory(category: String): List<Product> {
        return try {
            client.get("$BASE_URL/products/category/$category")
                .body<ProductResponse>()
                .products
        } catch (_: Exception) {
            emptyList()
        }
    }
}
