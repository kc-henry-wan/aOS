package com.wellbeing.pharmacyjob.utils

import com.wellbeing.pharmacyjob.model.RegUserRequest
import com.wellbeing.pharmacyjob.model.UserUpdateRequest

object UserDataValidator {

    fun validateRegUserRequest(request: RegUserRequest): List<String> {
        val errors = mutableListOf<String>()

        // Validate email
        if (request.email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(request.email)
                .matches()
        ) {
            errors.add("Invalid email address.")
        }

        // Validate first name
        if (request.firstName.isBlank()) {
            errors.add("First name is required.")
        }

        // Validate last name
        if (request.lastName.isBlank()) {
            errors.add("Last name is required.")
        }

        // Validate mobile number
        if (request.mobile.isBlank() || !request.mobile.matches(Regex("^[0-9]{10,15}$"))) {
            errors.add("Mobile number must be between 10 and 15 digits.")
        }

        // Validate address1
        if (request.address1.isBlank()) {
            errors.add("Address line 1 is required.")
        }

        // Validate postal code
        if (request.postalCode.isBlank()) {
            errors.add("Postal code is required.")
        } else if (request.postalCode.length < 6) {
            errors.add("Postal code must be at least 6 characters long.")
        }

        return errors
    }


    fun validateUserRequest(request: UserUpdateRequest): List<String> {
        val errors = mutableListOf<String>()

        // Validate first name
        if (request.firstName.isBlank()) {
            errors.add("First name is required.")
        }

        // Validate last name
        if (request.lastName.isBlank()) {
            errors.add("Last name is required.")
        }

        // Validate mobile number
        if (request.mobile.isBlank() || !request.mobile.matches(Regex("^[0-9]{10,15}$"))) {
            errors.add("Mobile number must be between 10 and 15 digits.")
        }

        // Validate address1
        if (request.address1.isBlank()) {
            errors.add("Address line 1 is required.")
        }

        // Validate postal code
        if (request.postalCode.isBlank()) {
            errors.add("Postal code is required.")
        } else if (request.postalCode.length < 6) {
            errors.add("Postal code must be at least 6 characters long.")
        }

        return errors
    }

}
