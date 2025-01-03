package com.android.banca_android.utils.extension

import android.app.Activity
import android.content.Intent

inline fun <reified T : Activity> Activity.launchActivity(init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}