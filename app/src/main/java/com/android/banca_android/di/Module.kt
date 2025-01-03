package com.android.banca_android.di

import com.android.banca_android.domain.data.local.service.Service
import com.android.banca_android.domain.data.remote.network.NetworkClient
import com.android.banca_android.domain.repository.banca.BancaRepository
import com.android.banca_android.domain.repository.banca.impl.BancaRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun getServices(): Service {
        return NetworkClient().getService()
    }
}
