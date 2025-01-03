package com.android.banca_android.di

import android.content.SharedPreferences
import com.android.banca_android.domain.repository.auth.AuthRepository
import com.android.banca_android.domain.repository.auth.impl.AuthRepositoryImp
import com.android.banca_android.domain.repository.banca.BancaRepository
import com.android.banca_android.domain.repository.banca.impl.BancaRepositoryImp
import com.android.banca_android.domain.repository.card.CardRepository
import com.android.banca_android.domain.repository.card.impl.CardRepositoryImp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): BancaRepository {
        return BancaRepositoryImp(database, storageReference)
    }

    @Provides
    @Singleton
    fun provideAutghRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImp(auth, database, appPreferences, gson)
    }

    @Provides
    @Singleton
    fun provideCardRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): CardRepository {
        return CardRepositoryImp(database, storageReference)
    }
}