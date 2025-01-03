package com.android.banca_android.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.banca_android.common.UiState
import com.android.banca_android.domain.data.entity.LoginModel
import com.android.banca_android.domain.data.entity.auth.User
import com.android.banca_android.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val repository: AuthRepository
): ViewModel() {

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
            get() = _register

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    fun register(
        email: String,
        password: String,
        user: User
    ) {
        _register.value = UiState.Loading
        repository.registerUser(
            email = email,
            password = password,
            user = user
        ) { _register.value = it }
    }

    fun login(
        data: LoginModel
    ) {
        _login.value = UiState.Loading
        repository.loginUser(
            data.username,
            data.password
        ){
            _login.value = it
        }
    }

    fun logout(result: () -> Unit){
        repository.logout(result)
    }

    fun getSession(result: (User?) -> Unit){
        repository.getSession(result)
    }
}