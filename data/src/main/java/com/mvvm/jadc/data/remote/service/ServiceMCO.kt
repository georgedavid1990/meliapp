package com.mvvm.jadc.data.remote.service

import com.google.gson.JsonObject
import com.mvvm.jadc.data.base.BaseResult
import com.mvvm.jadc.data.model.Product
import com.mvvm.jadc.data.model.ProductDetail
import com.mvvm.jadc.data.model.ProductDetailItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceMCO {
    @GET("/sites/MCO/search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): BaseResult<List<Product>>

    @GET("/items")
    suspend fun product(
        @Header("Authorization") token: String,
        @Query("ids") ids: String
    ): List<ProductDetailItem>
}