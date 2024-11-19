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
import com.wellbeing.pharmacyjob.databinding.ActivityCreateAccountBinding
import com.wellbeing.pharmacyjob.factory.RegisterNewUserViewModelFactory
import com.wellbeing.pharmacyjob.model.RegUserRequest
import com.wellbeing.pharmacyjob.repository.RegisterNewUserRepository
import com.wellbeing.pharmacyjob.utils.UserDataValidator.validateRegUserRequest
import com.wellbeing.pharmacyjob.viewmodel.RegisterNewUserViewModel

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var dataEmail: EditText
    private lateinit var dataPassword: EditText
    private lateinit var dataFirstname: EditText
    private lateinit var dataLastname: EditText
    private lateinit var dataMobile: EditText
    private lateinit var dataAddress1: EditText
    private lateinit var dataAddress2: EditText
    private lateinit var dataPostalCode: EditText
    private lateinit var createAccountButton: Button
    private lateinit var apiResultTextView: TextView
    private lateinit var registerNewUserViewModel: RegisterNewUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataEmail = binding.dataEmail
        dataPassword = binding.dataPassword
        dataFirstname = binding.dataFirstname
        dataLastname = binding.dataLastname
        dataMobile = binding.dataMobile
        dataAddress1 = binding.dataAddress1
        dataAddress2 = binding.dataAddress2
        dataPostalCode = binding.dataPostalCode
        createAccountButton = binding.createAccountButton
        apiResultTextView = binding.apiResultTextView

        val apiService = RetrofitInstance.api // Your Retrofit API service
        val repository = RegisterNewUserRepository(apiService)

        registerNewUserViewModel =
            ViewModelProvider(this, RegisterNewUserViewModelFactory(repository))
                .get(RegisterNewUserViewModel::class.java)

        // Observe the ViewModel LiveData for API response status
        registerNewUserViewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is ApiResult.Success -> {
                    apiResultTextView.text = getString(R.string.api_update_success)

                    // Return to LoginActivity after successful account creation
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()  // Close CreateAccountActivity
                }

                is ApiResult.Error -> {
                    apiResultTextView.text = getString(R.string.api_update_fail)
                }
            }
        })

        createAccountButton.setOnClickListener {
            val request = RegUserRequest(
                "8",
                dataEmail.text.toString(),
                dataFirstname.text.toString(),
                dataLastname.text.toString(),
                dataMobile.text.toString(),
                dataAddress1.text.toString(),
                dataAddress2.text.toString(),
                dataPostalCode.text.toString()
            )

            val errors = validateRegUserRequest(request)
            if (errors.size == 0) {
                registerNewUserViewModel.registerNewUser(request)
            } else {
                apiResultTextView.text = errors.joinToString(separator = "\n")
                apiResultTextView.setTextColor(Color.RED)
            }
        }
    }

}
