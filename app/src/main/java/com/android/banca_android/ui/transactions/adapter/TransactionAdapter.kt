package com.android.banca_android.ui.transactions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.banca_android.domain.data.entity.card.CardModel
import com.android.mibanca.R
import com.android.mibanca.databinding.ItemTransactionBinding

class TransactionAdapter(private val transactions: List<CardModel>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.onBind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class TransactionViewHolder(var binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(transaction: CardModel) =
            with(binding) {
                val context = this.root.context
                textViewCardUsed.text =
                    context.getString(R.string.card_number, transaction.numberCard)
                textViewRecipientCard.text =
                    context.getString(R.string.card_name, transaction.nameCard)
                textViewRecipientName.text =
                    context.getString(R.string.card_type, transaction.typeCard)
                textViewPaymentReason.text =
                    context.getString(R.string.card_description, transaction.description)
                dateTimeTextView.text =
                    context.getString(R.string.card_date, transaction.date.toString())
                locationTextView.text = "Mexico"
            }
    }
}