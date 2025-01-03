package com.android.banca_android.domain.data.entity.transaction

data class TransactionModel(
    val cardUsed: String,
    val recipientCard: String,
    val recipientName: String,
    val paymentReason: String,
    val dateTime: String,
    val location: String
)
