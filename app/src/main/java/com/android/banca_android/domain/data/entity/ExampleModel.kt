package com.android.banca_android.domain.data.entity

data class ExampleModel(
    val response: Response,
    val statusCode: Int,
    val statusMessage: String
)

data class Response(
    val auth: Boolean,
    val token: String
)