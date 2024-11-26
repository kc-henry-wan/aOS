package com.wellbeing.pharmacyjob.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.MainActivity
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.api.RetrofitInstance
import com.wellbeing.pharmacyjob.api.SessionManager
import com.wellbeing.pharmacyjob.databinding.ActivityLoginBinding
import com.wellbeing.pharmacyjob.factory.LoginViewModelFactory
import com.wellbeing.pharmacyjob.repository.LoginRepository
import com.wellbeing.pharmacyjob.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var createAccountLink: TextView
    private lateinit var forgotPasswordLink: TextView
    private lateinit var logonResultTextView: TextView
    private lateinit var loginViewModel: LoginViewModel
//    private lateinit var gotoHomeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usernameEditText = binding.usernameEditText
        passwordEditText = binding.passwordEditText
        loginButton = binding.loginButton
        createAccountLink = binding.createAccountLink
        forgotPasswordLink = binding.forgotPasswordLink
        logonResultTextView = binding.logonResultTextView
//        gotoHomeButton = binding.gotoHomeButton

        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Initialize the repository and ViewModel
        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = LoginRepository(apiService)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(repository))
            .get(LoginViewModel::class.java)

        loginViewModel.loginLiveData.observe(this, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    if (result.data?.apiStatus == "Success") {
                        // Handle successful login, navigate to the next screen
                        logonResultTextView.text = "Login Successful!"
                        SessionManager.createSession(
                            this,
                            result.data?.data?.sessionKey.toString(),
                            result.data?.data?.userId.toString(),
                            result.data?.data?.userLat.toString(),
                            result.data?.data?.userLng.toString()
                        )
                        navigateToHomeScreen()
                    } else {
                        SessionManager.logout(this)
                        logonResultTextView.setTextColor(Color.RED)
                        logonResultTextView.text = "Login failed: " + result.data?.errorMessage
                    }
                }

                is ApiResult.Error -> {
                    // Show error message
                    SessionManager.logout(this)
                    logonResultTextView.setTextColor(Color.RED)
                    logonResultTextView.text = "Login failed: " + result.message
                }
            }
        })

        loginButton.setOnClickListener {
            if (loginValidation(username, password)) {
                loginViewModel.login(username, password)
            } else {
                Toast.makeText(this, "Input error", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener to go to the CreateAccountActivity
        createAccountLink.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        // Set click listener to go to the ForgotPasswordActivity
        forgotPasswordLink.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

//        gotoHomeButton.setOnClickListener {
//            navigateToHomeScreen()
//        }
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginValidation(username: String, password: String): Boolean {
        //Validation logic
        return true
    }

    private fun showRetryDialog(message: String, retryAction: () -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Retry") { dialog, _ ->
                // Retry the API call
                retryAction()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        val alert = builder.create()
        alert.show()
    }

}
