package com.mvvm.jadc.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("site_id") val siteId: String,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double?,
    @SerializedName("currency_id") val currencyId: String?,
    @SerializedName("available_quantity") val availableQuantity: Int?,
    @SerializedName("thumbnail") val thumbnail: String?
)