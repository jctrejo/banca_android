package com.android.banca_android.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.banca_android.common.UiState
import com.android.banca_android.ui.cards.CardViewModel
import com.android.banca_android.ui.register.AuthViewModel
import com.android.banca_android.ui.transactions.adapter.TransactionAdapter
import com.android.banca_android.utils.extension.toast
import com.android.mibanca.R
import com.android.mibanca.databinding.FragmentTransactionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModels()
    private val cardViewModel: CardViewModel by viewModels()
    private val binding by lazy { FragmentTransactionsBinding.inflate(layoutInflater) }

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
            cardViewModel.getPayments(it)
        }

        recyclerViewTransactions.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observer() = with(binding) {
        cardViewModel.payment.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    requireActivity().toast(getString(R.string.loader))
                }

                is UiState.Failure -> {
                    requireActivity().toast(state.error.toString())
                }

                is UiState.Success -> {
                    val adapter = TransactionAdapter(state.item)
                    recyclerViewTransactions.adapter = adapter
                }
            }
        }
    }
}