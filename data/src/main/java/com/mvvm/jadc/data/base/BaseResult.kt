package com.mvvm.jadc.data.base

data class BaseResult<T>(
    val siteId: String,
    val query: String,
    val paging: Paging,
    val results: T
)
