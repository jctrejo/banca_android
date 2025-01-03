package com.android.banca_android.common

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val item: T) : UiState<T>()
    data class Failure(val error: String?): UiState<Nothing>()
}
