package com.wellbeing.pharmacyjob.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var createAccountButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usernameEditText = binding.usernameEditText
        passwordEditText = binding.passwordEditText
        createAccountButton = binding.createAccountButton

        createAccountButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                // TODO: Call API
                Toast.makeText(this, getString(R.string.api_update_success), Toast.LENGTH_SHORT)
                    .show()

                // Return to LoginActivity after successful account creation
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()  // Close CreateAccountActivity
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
