package com.mvvm.jadc.domain.model

data class ProductUI(
    val id: String,
    val name: String,
    val thumbnailUrl: String?,
    val price: Double,
    val stock: Int
)