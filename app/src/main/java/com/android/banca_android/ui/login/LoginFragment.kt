package com.android.banca_android.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.banca_android.common.UiState
import com.android.banca_android.domain.data.entity.LoginModel
import com.android.banca_android.ui.init.InitActivity
import com.android.banca_android.ui.register.AuthViewModel
import com.android.banca_android.utils.extension.afterTextChanged
import com.android.banca_android.utils.extension.errorInputLayout
import com.android.banca_android.utils.extension.isValidAlphanumericPassword
import com.android.banca_android.utils.extension.launchActivity
import com.android.banca_android.utils.extension.toast
import com.android.banca_android.utils.extension.validateEmail
import com.android.mibanca.R
import com.android.mibanca.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

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

        acceptButton.setOnClickListener {
            if (validateData()) {
                val data = LoginModel(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
                viewModel.login(data)
            }
        }

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun goToHome() {
        requireActivity().launchActivity<InitActivity> {}
    }

    private fun observer() {
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    requireActivity().toast(getString(R.string.loader))
                }

                is UiState.Failure -> {
                    requireActivity().toast(state.error.toString())
                }

                is UiState.Success -> {
                    goToHome()
                }
            }
        }
    }

    private fun validateData(): Boolean = with(binding) {
        passwordEditText.afterTextChanged { passwordLayout.error = null }
        emailEditText.afterTextChanged { emailLayout.error = null }

        if (!emailEditText.text.toString().validateEmail()) {
            emailLayout.errorInputLayout("Debes agregar un correo correcto")
            return false
        } else if (!passwordEditText.text.toString().isValidAlphanumericPassword()) {
            passwordLayout.errorInputLayout("Debes agregar caracteres alfanumericos y que sean 8 digitos")
            return false
        } else {
            return true
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getSession { user ->
            if (user != null) {
                goToHome()
            }
        }
    }
}
