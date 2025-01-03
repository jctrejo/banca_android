package com.android.banca_android.domain.data.entity.card

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class CardModel(
    var id: String = "",
    var user_id: String = "",
    val nameCard: String = "",
    val numberCard: String? = "",
    val dateExpiration: String? = "",
    @ServerTimestamp
    val date: Date = Date(),
    val typeCard: String? = "",
    val description: String = ""
)


