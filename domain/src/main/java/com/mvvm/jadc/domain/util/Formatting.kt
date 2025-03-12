package com.mvvm.jadc.domain.util

import java.text.NumberFormat
import java.util.Locale

fun Double.formatCurrency(locale: Locale = Locale.US): String {
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return formatter.format(this)
}