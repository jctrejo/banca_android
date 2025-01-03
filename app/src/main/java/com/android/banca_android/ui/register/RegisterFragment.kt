package com.android.banca_android.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.banca_android.common.UiState
import com.android.banca_android.domain.data.entity.auth.User
import com.android.banca_android.utils.extension.isValidEmail
import com.android.banca_android.utils.extension.toast
import com.android.mibanca.R
import com.android.mibanca.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observer()
    }

    private fun initView() = with(binding) {
        binding.registerBtn.setOnClickListener {
            if (validation()) {
                viewModel.register(
                    email = binding.emailEt.text.toString(),
                    password = binding.passEt.text.toString(),
                    user = getUserObj()
                )
            }
        }
    }

    private fun observer() {
        viewModel.register.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    requireActivity().toast(getString(R.string.loader))
                }

                is UiState.Failure -> {
                    binding.registerBtn.text = state.error
                }

                is UiState.Success -> {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }

    private fun getUserObj(): User {
        return User(
            id = "",
            first_name = binding.firstNameEt.text.toString(),
            last_name = binding.lastNameEt.text.toString(),
            email = binding.emailEt.text.toString(),
        )
    }

    private fun validation(): Boolean {
        var isValid = true

        if (binding.firstNameEt.text.isNullOrEmpty()) {
            isValid = false
            requireActivity().toast(getString(R.string.enter_first_name))
        }

        if (binding.lastNameEt.text.isNullOrEmpty()) {
            isValid = false
            requireActivity().toast(getString(R.string.enter_last_name))
        }

        if (binding.emailEt.text.isNullOrEmpty()) {
            isValid = false
            requireActivity().toast(getString(R.string.enter_email))
        } else {
            if (!binding.emailEt.text.toString().isValidEmail()) {
                isValid = false
                requireActivity().toast(getString(R.string.invalid_email))
            }
        }
        if (binding.passEt.text.isNullOrEmpty()) {
            isValid = false
            requireActivity().toast(getString(R.string.enter_password))
        } else {
            if (binding.passEt.text.toString().length < 8) {
                isValid = false
                requireActivity().toast(getString(R.string.invalid_password))
            }
        }
        return isValid
    }

}