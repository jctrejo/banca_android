package com.android.banca_android.ui.payment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.banca_android.common.UiState
import com.android.banca_android.domain.data.entity.card.CardModel
import com.android.banca_android.ui.cards.CardViewModel
import com.android.banca_android.ui.register.AuthViewModel
import com.android.banca_android.utils.CardNumberMask
import com.android.banca_android.utils.extension.toast
import com.android.mibanca.R
import com.android.mibanca.databinding.FragmentPaymentBinding
import com.google.firebase.firestore.ServerTimestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModels()
    private val cardViewModel: CardViewModel by viewModels()
    private val binding by lazy { FragmentPaymentBinding.inflate(layoutInflater) }
    private var useCardSaved = false
    private var selectedCard: String = ""
    private var recipientCard: String = ""
    private var recipientName: String = ""
    private var paymentReason: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        observer()

        authViewModel.getSession {
            cardViewModel.getCards(it)
        }

        saveCardCheckBox.setOnClickListener {
            if (saveCardCheckBox.isChecked) {
                useCardSaved = true
                editTextRecipientCard.isEnabled = false
            } else {
                useCardSaved = false
                editTextRecipientCard.isEnabled = true
            }

            val selectedCard = spinnerConfiguredCard.selectedItem.toString()
            editTextRecipientCard.setText(selectedCard)
        }

        editTextRecipientCard.addTextChangedListener(
            CardNumberMask(
                editTextRecipientCard,
                emptyTextView,
                emptyImageView
            )
        )

        buttonSubmitPayment.setOnClickListener {
            val selectedCard = spinnerConfiguredCard.selectedItem.toString()
            recipientCard = editTextRecipientCard.text.toString()
            recipientName = editTextRecipientName.text.toString()
            paymentReason = editTextPaymentReason.text.toString()

            if (isValidPayment(selectedCard, recipientCard, recipientName, paymentReason) ) {
                val payment = CardModel(
                    user_id = "",
                    nameCard = recipientName,
                    numberCard = if (useCardSaved) selectedCard else recipientCard,
                    dateExpiration = "",
                    typeCard = "visa",
                    description = paymentReason
                )
                cardViewModel.addPayment(payment)
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.error)
                    .setMessage(R.string.please_complete_all_fields)
                    .setPositiveButton(getString(R.string.ok), null)
                    .show()
            }
        }
    }

    private fun setupSpinner(item: List<CardModel>) = with(binding) {
        val adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            item.map { it.numberCard }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerConfiguredCard.adapter = adapter
    }

    private fun observer() {
        cardViewModel.cards.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    requireActivity().toast(getString(R.string.loader))
                }

                is UiState.Failure -> {
                    requireActivity().toast(state.error.toString())
                }

                is UiState.Success -> {
                    setupSpinner(state.item)
                }
            }
        }

        cardViewModel.addPayment.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    requireActivity().toast(getString(R.string.loader))
                }

                is UiState.Failure -> {
                    requireActivity().toast(state.error.toString())
                }

                is UiState.Success -> {
                    showConfirmationDialog(selectedCard, recipientCard, recipientName, paymentReason)
                }
            }
        }
    }

    private fun isValidPayment(
        selectedCard: String,
        recipientCard: String,
        recipientName: String,
        paymentReason: String
    ): Boolean {
        return selectedCard.isNotBlank() && recipientCard.isNotBlank() && recipientName.isNotBlank() && paymentReason.isNotBlank()
    }

    private fun showConfirmationDialog(
        selectedCard: String,
        recipientCard: String,
        recipientName: String,
        paymentReason: String
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle(requireContext().getString(R.string.confirm_payment))
            .setMessage(
                getString(
                    R.string.confirm_payment_message,
                    selectedCard,
                    recipientCard,
                    recipientName,
                    paymentReason
                )
            )
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.success_title)
                    .setMessage(R.string.payment_success_message)
                    .setPositiveButton(getString(R.string.ok), null)
                    .show()
            }
            .setNegativeButton(getString(R.string.cancel_paymentq), null)
            .show()
    }
}
