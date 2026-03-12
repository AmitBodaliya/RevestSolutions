package com.abapp.revestsolutions.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val brand: String? = null,
    val rating: Double? = null,
    val thumbnail: String? = null,
)