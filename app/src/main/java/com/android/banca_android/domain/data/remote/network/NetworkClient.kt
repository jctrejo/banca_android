package com.android.banca_android.domain.data.remote.network

import com.android.banca_android.common.Constants.URL_BASE
import com.android.banca_android.domain.data.local.service.Service
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

class NetworkClient {

    private val api = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpClient())

    fun getService(): Service {
        return api
            .build()
            .create(Service::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .dns(Dns.SYSTEM)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private fun provideOkHttpClientHostnameVerifier(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().hostnameVerifier( HostnameVerifier { hostname, session ->
            return@HostnameVerifier true
        })
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}
