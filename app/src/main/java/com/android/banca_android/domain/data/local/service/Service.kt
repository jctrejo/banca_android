package com.android.banca_android.domain.data.local.service


import com.android.banca_android.domain.data.entity.ExampleModel
import com.android.banca_android.domain.data.entity.LoginModel
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {

    @POST("api/example")
    suspend fun getExample(@Body data: LoginModel): ExampleModel
}
