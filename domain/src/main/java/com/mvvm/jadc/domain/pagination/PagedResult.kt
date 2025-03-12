package com.mvvm.jadc.domain.pagination

data class PagedResult<T>(
    val results: List<T>,
    val total: Int,
    val offset: Int,
    val limit: Int
) {
    companion object {
        fun <T> empty() = PagedResult<T>(emptyList(), 0, 0, 0)
    }
}