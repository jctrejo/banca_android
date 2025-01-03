package com.android.banca_android.domain.repository.banca.impl

import android.net.Uri
import com.android.banca_android.domain.data.entity.auth.User
import com.android.banca_android.domain.repository.banca.BancaRepository
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class BancaRepositoryImp(
    val database: FirebaseFirestore,
    val storageReference: StorageReference
) : BancaRepository {


}
