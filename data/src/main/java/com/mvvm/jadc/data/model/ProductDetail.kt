package com.mvvm.jadc.data.model

import com.google.gson.annotations.SerializedName

data class ProductDetailItem(
    @SerializedName("body") val body: ProductDetail
)

data class ProductDetail(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("pictures") val pictures: List<Picture>,
    @SerializedName("price") val price: Double?,
    @SerializedName("available_quantity") val availableQuantity: Int?,
    @SerializedName("attributes") val attributes: List<Attribute>
)


data class Picture(
    @SerializedName("url") val url: String
)

data class Attribute(
    @SerializedName("name") val name: String,
    @SerializedName("value_name") val valueName: String

)