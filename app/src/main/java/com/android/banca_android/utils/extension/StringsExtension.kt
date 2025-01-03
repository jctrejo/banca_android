package com.android.banca_android.utils.extension

import android.content.Context
import android.util.Patterns
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String.validateEmail(): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(this).matches()
}

fun String.isValidAlphanumericPassword(): Boolean {
    return this.length == 8 && this.all { it.isLetterOrDigit() }
}

fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()