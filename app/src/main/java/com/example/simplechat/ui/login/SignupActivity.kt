package com.example.simplechat.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.simplechat.MainApplication
import com.example.simplechat.R
import com.example.simplechat.databinding.ActivitySignupBinding
import com.example.simplechat.response.ResponseType
import com.example.simplechat.response.ViewResponse
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signUpViewModel: SignUpViewModel by viewModels {
            SignUpViewModelFactory((this.application as MainApplication).authService)
        }

        // TODO : [Remove] Data inflation for testing
        binding.signupFirstNameTextField.setText("Himanshu")
        binding.signupLastNameTextField.setText("Choudhary")
        binding.signupEmailTextField.setText("himanshu@example.com")
        binding.signupPasswordTextField.setText("password")
        binding.signupConfirmPasswordTextField.setText("password")

        binding.signupSubmitButton.setOnClickListener {
            var firstName: String = binding.signupFirstNameTextField.text.toString()
            var lastName: String = binding.signupLastNameTextField.text.toString()
            var email: String = binding.signupEmailTextField.text.toString()
            var password: String = binding.signupPasswordTextField.text.toString()
            signUpViewModel.signUp(firstName, lastName, email, password)
        }

        lifecycleScope.launch {
            signUpViewModel.signUpResponseState.collect {
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
        if(viewResponse.responseType.equals(ResponseType.SUCCESS)) {
            finish()
        }
    }
}