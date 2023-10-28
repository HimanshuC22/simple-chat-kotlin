package com.example.simplechat.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
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

class SignUpViewModel(
    private var authService: AuthService,
) : ViewModel() {


    val signUpResponseState: MutableStateFlow<ViewResponse> =
        MutableStateFlow(ViewResponse.loading())

    fun signUp(firstName: String, lastName: String, email: String, password: String) {
        signUpResponseState.value = ViewResponse.loading()
        viewModelScope.launch(Dispatchers.IO) {
            authService.signUp(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password
            ).flowOn(Dispatchers.IO)
                .collectLatest { response ->
                    when (response) {
                        is FlowResponse.Success -> {
                            signUpResponseState.value =
                                ViewResponse.success("[ViewModel] Successful ${response.message}")
                        }

                        else -> {
                            signUpResponseState.value = ViewResponse(
                                ResponseType.ERROR,
                                "[ViewModel] Something went wrong"
                            )
                        }
                    }
                }
        }
    }
}

class SignUpViewModelFactory(
    private val authService: AuthService,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}