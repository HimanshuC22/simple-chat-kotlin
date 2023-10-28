package com.example.simplechat.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.simplechat.data.models.User
import com.example.simplechat.response.FlowResponse
import com.example.simplechat.response.ResponseType
import com.example.simplechat.response.ViewResponse
import com.example.simplechat.services.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LoginViewModel(
    private var authService: AuthService,
) : ViewModel() {

    init {
        viewModelScope.launch {
            val user : User? = authService.getCurrentUser()
            if(user != null) {
                loginResponseState.value = ViewResponse(ResponseType.SUCCESS, "User already logged in")
            }
        }
    }

    val loginResponseState: MutableStateFlow<ViewResponse> =
        MutableStateFlow(ViewResponse.loading())

    fun login(username: String, password: String) {
        loginResponseState.value = ViewResponse.loading()
        viewModelScope.launch(Dispatchers.IO) {
            authService.login(username, password).flowOn(Dispatchers.IO)
                .collectLatest { response ->
                    Log.d("LoginViewModel", response.message)
                    when (response) {
                        is FlowResponse.Success -> {
                            loginResponseState.value =
                                ViewResponse.success("[ViewModel] Successful ${response.message}")
                        }

                        else -> {
                            loginResponseState.value = ViewResponse(
                                ResponseType.ERROR,
                                "[ViewModel] ${response.message}"
                            )
                        }
                    }
                }
        }
    }
}

class LoginViewModelFactory(
    private val authService: AuthService,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}