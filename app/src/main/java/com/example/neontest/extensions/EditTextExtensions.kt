package com.example.neontest.extensions

import android.widget.EditText

var EditText.content: String
    get() = text.toString()
    set(value) = setText(value)