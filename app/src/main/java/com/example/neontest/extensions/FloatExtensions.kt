package com.example.neontest.extensions

import java.text.NumberFormat

fun Float.toMoney(maximumFractionDigits: Int = 0): String =
    NumberFormat.getCurrencyInstance().run {
        this.maximumFractionDigits = maximumFractionDigits
        format(this@toMoney)
    }