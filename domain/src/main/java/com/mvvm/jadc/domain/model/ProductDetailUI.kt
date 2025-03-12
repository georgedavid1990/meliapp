package com.mvvm.jadc.domain.model

data class ProductDetailUI(
    val id: String,
    val name: String,
    val pictures: List<String>,
    val price: Double,
    val stock: Int,
    val attributes: List<String>
)
