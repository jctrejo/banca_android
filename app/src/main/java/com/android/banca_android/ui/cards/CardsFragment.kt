package com.android.banca_android.ui.cards

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.banca_android.common.UiState
import com.android.banca_android.domain.data.entity.card.CardModel
import com.android.banca_android.ui.init.MainActivity
import com.android.banca_android.ui.register.AuthViewModel
import com.android.banca_android.utils.CardNumberMask
import com.android.banca_android.utils.card.CardEndDateMask
import com.android.banca_android.utils.extension.launchActivity
import com.android.banca_android.utils.extension.toast
import com.android.mibanca.R
import com.android.mibanca.databinding.FragmentCardsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment : Fragment() {

    private val viewModel: CardViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val binding by lazy { FragmentCardsBinding.inflate(layoutInflater) }
    private val cardList = mutableListOf<String>()
    private lateinit var cardHolderName: String
    private lateinit var cardNumber: String
    private lateinit var expirationDate: String
    private lateinit var adapter: CardsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        initView()
        observer()
    }

    private fun initView() = with(binding) {

        authViewModel.getSession {
            viewModel.getCards(it)
        }

        buttonAddCard.setOnClickListener {
            showAddCardDialog()
        }

        logOutButton.setOnClickListener {
            authViewModel.logout {
                requireActivity().finish()
                goToLogin()
            }
        }
    }

    private fun setupAdapter() {
        adapter = CardsAdapter(CardsAdapter.OnClickListener { item, position, isSelect -> })
        binding.cardRecyclerView.adapter = adapter
    }

    private fun loaderAdapter(lines: List<CardModel>) {
        if (::adapter.isInitialized) {
            lines.let {
                if (it.isNotEmpty()) {
                    cardList.clear()
                    it.forEach { data ->
                        cardList.add(data.nameCard + " - " + data.numberCard + " - " + data.dateExpiration)
                    }
                    adapter.submitList(it.toList() ?: emptyList())
                }
            }
        }
    }

    private fun goToLogin() {
        requireActivity().launchActivity<MainActivity> { }
    }

    private fun observer() {
        viewModel.addCard.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    requireActivity().toast(getString(R.string.loader))
                }

                is UiState.Failure -> {
                    requireActivity().toast(state.error.toString())
                }

                is UiState.Success -> {
                    requireActivity().toast(getString(R.string.save_success))
                }
            }
        }

        viewModel.cards.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    requireActivity().toast(getString(R.string.loader))
                }

                is UiState.Failure -> {
                    requireActivity().toast(state.error.toString())
                }

                is UiState.Success -> {
                    loaderAdapter(state.item)
                }
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddCardDialog() = with(binding) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_card, null)
        val editTextCardHolderName = dialogView.findViewById<EditText>(R.id.editTextCardHolderName)
        val editTextCardNumber = dialogView.findViewById<EditText>(R.id.editTextCardNumber)
        val editTextExpirationDate = dialogView.findViewById<EditText>(R.id.editTextExpirationDate)
        val tvCard = dialogView.findViewById<TextView>(R.id.tv_card)
        val logo = dialogView.findViewById<ImageView>(R.id.imageView_card_logo)
        val yearTextView = dialogView.findViewById<TextView>(R.id.yearTextView)

        editTextCardNumber?.addTextChangedListener(
            CardNumberMask(
                editTextCardNumber,
                tvCard,
                logo
            )
        )

        editTextExpirationDate?.addTextChangedListener(
            CardEndDateMask(
                editTextExpirationDate,
                yearTextView
            )
        )

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_card)
            .setView(dialogView)
            .setPositiveButton(R.string.save) { _, _ ->
                cardHolderName = editTextCardHolderName.text.toString()
                cardNumber = editTextCardNumber.text.toString()
                expirationDate = editTextExpirationDate.text.toString()

                if (cardHolderName.isNotBlank() && cardNumber.isNotBlank() && expirationDate.isNotBlank()) {
                    val card = CardModel(
                        cardHolderName,
                        cardHolderName,
                        cardHolderName,
                        cardNumber,
                        expirationDate
                    )
                    viewModel.addCard(card)
                    authViewModel.getSession {
                        viewModel.getCards(it)
                    }
                }
            }
            .setNegativeButton(getString(R.string.cancel_paymentq), null)
            .show()
    }
}
