package com.android.banca_android.ui.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.banca_android.common.UiState
import com.android.banca_android.domain.data.entity.auth.User
import com.android.banca_android.domain.data.entity.card.CardModel
import com.android.banca_android.domain.repository.card.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    val repository: CardRepository
) : ViewModel() {

    private val _addCard = MutableLiveData<UiState<Pair<CardModel, String>>>()
    val addCard: LiveData<UiState<Pair<CardModel, String>>>
        get() = _addCard

    private val _addPayment = MutableLiveData<UiState<Pair<CardModel, String>>>()
    val addPayment: LiveData<UiState<Pair<CardModel, String>>>
        get() = _addPayment

    private val _cards = MutableLiveData<UiState<List<CardModel>>>()
    val cards: LiveData<UiState<List<CardModel>>>
        get() = _cards

    private val _payments = MutableLiveData<UiState<List<CardModel>>>()
    val payment: LiveData<UiState<List<CardModel>>>
        get() = _payments

    fun addCard(data: CardModel) {
        _addCard.value = UiState.Loading
        repository.addCard(data) { _addCard.value = it }
    }

    fun getCards(user: User?) {
        _cards.value = UiState.Loading
        repository.getCards(user) { _cards.value = it }
    }

    fun addPayment(data: CardModel) {
        _addPayment.value = UiState.Loading
        repository.addPayment(data) { _addPayment.value = it }
    }

    fun getPayments(user: User?) {
        _payments.value = UiState.Loading
        repository.getPayments(user) { _payments.value = it }
    }
}