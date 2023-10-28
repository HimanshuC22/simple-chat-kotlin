package com.example.simplechat.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simplechat.MainActivity
import com.example.simplechat.MainApplication
import com.example.simplechat.R
import com.example.simplechat.data.api.AuthApi
import com.example.simplechat.data.api.RetrofitClient
import com.example.simplechat.data.models.response.LoginResponse
import com.example.simplechat.data.storage.AppDatabase
import com.example.simplechat.databinding.ActivityLoginBinding
import com.example.simplechat.response.ResponseType
import com.example.simplechat.response.ViewResponse
import com.example.simplechat.services.AuthService
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginViewModel: LoginViewModel by viewModels {
            LoginViewModelFactory((this.application as MainApplication).authService)
        }

        // TODO : [Remove] Data for testing
        binding.loginEmailEdittext.setText("himanshu@example.com")
        binding.loginPasswordEdittext.setText("password")

        // Email Validation
        binding.loginEmailEdittext.doOnTextChanged { text, start, before, count ->
            if (text != null) {
                if (text.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    binding.loginEmailEdittext.error = "Invalid Email Address"
                }
            }
        }

        binding.loginSubmitButton.setOnClickListener {
            loginViewModel.login(binding.loginEmailEdittext.text.toString(), binding.loginPasswordEdittext.text.toString())
        }

        binding.signupButton.setOnClickListener {
            val intent: Intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            loginViewModel.loginResponseState.collect {
                when (it.responseType) {
                    ResponseType.SUCCESS -> handleResponse(it)
                    ResponseType.ERROR -> handleResponse(it)
                    else -> {
                        Log.e("LoginActivity", "[Activity] [Other Error] ${it.message}")
                    }
                }
            }
        }
    }

    private fun handleResponse(viewResponse: ViewResponse) {
        Snackbar.make(binding.root, viewResponse.message.toString(), Snackbar.LENGTH_SHORT).show()
        if (viewResponse.responseType.equals(ResponseType.SUCCESS)) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}