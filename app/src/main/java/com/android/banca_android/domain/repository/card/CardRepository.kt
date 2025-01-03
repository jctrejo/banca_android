package com.android.banca_android.domain.repository.card

import com.android.banca_android.common.UiState
import com.android.banca_android.domain.data.entity.auth.User
import com.android.banca_android.domain.data.entity.card.CardModel

interface CardRepository {
    fun addCard(data: CardModel, result: (UiState<Pair<CardModel, String>>) -> Unit)
    fun getCards(user: User?, result: (UiState<List<CardModel>>) -> Unit)
    fun addPayment(data: CardModel, result: (UiState<Pair<CardModel, String>>) -> Unit)
    fun getPayments(user: User?, result: (UiState<List<CardModel>>) -> Unit)
}
