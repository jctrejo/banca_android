package com.android.banca_android.common

sealed class AppUiState<T> {
    class Loading<T> : AppUiState<T>()
    data class Success<T>(val data: T) : AppUiState<T>()
    data class Error<T>(val error: String) : AppUiState<T>()
}
