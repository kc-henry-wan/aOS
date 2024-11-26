package com.wellbeing.pharmacyjob.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.api.RetrofitInstance
import com.wellbeing.pharmacyjob.databinding.ActivityForgotPasswordBinding
import com.wellbeing.pharmacyjob.factory.RequestPwResetViewModelFactory
import com.wellbeing.pharmacyjob.repository.RequestPwResetRepository
import com.wellbeing.pharmacyjob.utils.UserDataValidator.validateEmail
import com.wellbeing.pharmacyjob.viewmodel.RequestPwResetViewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var dataEmail: EditText
    private lateinit var forgotPasswordButton: Button
    private lateinit var apiResultTextView: TextView
    private lateinit var requestPwResetViewModel: RequestPwResetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataEmail = binding.dataEmail
        forgotPasswordButton = binding.forgotPasswordButton
        apiResultTextView = binding.apiResultTextView

        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = RequestPwResetRepository(apiService)

        requestPwResetViewModel =
            ViewModelProvider(this, RequestPwResetViewModelFactory(repository))
                .get(RequestPwResetViewModel::class.java)

        // Observe the ViewModel LiveData for API response status
        requestPwResetViewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    apiResultTextView.text = getString(R.string.api_update_success)

                    // Return to LoginActivity after successful account creation
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()  // Close ForgotPasswordActivity
                }

                is ApiResult.Error -> {
                    apiResultTextView.text = getString(R.string.api_update_fail)
                }
            }
        })

        forgotPasswordButton.setOnClickListener {
            val txtEmail = dataEmail.text.toString()
            val errors = validateEmail(txtEmail)
            
            if (errors.size == 0) {
                requestPwResetViewModel.requestPwReset(txtEmail)
            } else {
                apiResultTextView.text = errors.joinToString(separator = "\n")
                apiResultTextView.setTextColor(Color.RED)
            }
        }
    }

}
