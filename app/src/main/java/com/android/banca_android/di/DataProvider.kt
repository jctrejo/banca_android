package com.android.banca_android.di

import android.content.Context
import com.linker.toolpizza.domain.local.preferences.Preferences
import com.linker.toolpizza.domain.local.preferences.PreferencesImpl

object DataProvider {
    fun providesPreferences(context: Context): Preferences = PreferencesImpl.getInstance(context)
}
