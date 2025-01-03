package com.android.banca_android.ui.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.banca_android.domain.data.entity.card.CardModel
import com.android.mibanca.databinding.ItemCardBinding

class CardsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<CardModel, CardsAdapter.CalendarViewHolder>(
        COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, positionCalendar: Int) {
        val item = getItem(positionCalendar)
        holder.onBind(item, onClickListener, positionCalendar)
    }

    class OnClickListener(val clickListener: (item: CardModel, position: Int, isSelected: Boolean) -> Unit) {
        fun onClick(item: CardModel, position: Int, isSelected: Boolean) =
            clickListener(item, position, isSelected)
    }

    class CalendarViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(
            data: CardModel,
            clickListener: OnClickListener,
            position: Int
        ) =
            with(binding) {
                titleTextView.text =
                    data.nameCard + " - " + data.numberCard + " - " + data.dateExpiration
            }
    }

    object COMPARATOR : DiffUtil.ItemCallback<CardModel>() {
        override fun areItemsTheSame(
            oldItem: CardModel,
            newItem: CardModel,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CardModel,
            newItem: CardModel,
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}