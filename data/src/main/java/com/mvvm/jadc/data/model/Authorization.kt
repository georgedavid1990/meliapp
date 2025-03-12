package com.mvvm.jadc.data.model

data class Authorization(
    // Valid for 6hrs
    val token: String,
    // Valid for 6 months
    val refreshToken: String
)
