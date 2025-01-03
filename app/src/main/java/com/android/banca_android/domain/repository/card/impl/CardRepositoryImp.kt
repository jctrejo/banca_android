package com.android.banca_android.domain.repository.card.impl

import com.android.banca_android.common.FireStoreCollection
import com.android.banca_android.common.UiState
import com.android.banca_android.domain.data.entity.auth.User
import com.android.banca_android.domain.data.entity.card.CardModel
import com.android.banca_android.domain.repository.card.CardRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class CardRepositoryImp(
    private val database: FirebaseFirestore,
    val storageReference: StorageReference
) : CardRepository {

    override fun addCard(data: CardModel, result: (UiState<Pair<CardModel, String>>) -> Unit) {
        val document = database.collection(FireStoreCollection.CARD).document()
        data.id = document.id
        document
            .set(data)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(data, "Note has been created successfully"))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun getCards(user: User?, result: (UiState<List<CardModel>>) -> Unit) {
        database.collection(FireStoreCollection.CARD)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (value != null) {
                    val notes = arrayListOf<CardModel>()
                    value.forEach { document ->
                        val note = document.toObject(CardModel::class.java)
                        notes.add(note)
                    }
                    result.invoke(
                        UiState.Success(notes.sortedByDescending { it.date })
                    )
                } else {
                    result.invoke(
                        UiState.Failure("")
                    )
                }
            }
    }

    override fun addPayment(data: CardModel, result: (UiState<Pair<CardModel, String>>) -> Unit) {
        val document = database.collection(FireStoreCollection.PAYMENT).document()
        data.id = document.id
        document
            .set(data)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(data, "Note has been created successfully"))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun getPayments(user: User?, result: (UiState<List<CardModel>>) -> Unit) {
        database.collection(FireStoreCollection.PAYMENT)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (value != null) {
                    val notes = arrayListOf<CardModel>()
                    value.forEach { document ->
                        val note = document.toObject(CardModel::class.java)
                        notes.add(note)
                    }
                    result.invoke(
                        UiState.Success(notes.sortedByDescending { it.date })
                    )
                } else {
                    result.invoke(
                        UiState.Failure("")
                    )
                }
            }
    }
}
