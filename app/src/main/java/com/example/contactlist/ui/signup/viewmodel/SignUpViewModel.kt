package com.example.contactlist.ui.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contactlist.data.model.User
import kotlinx.coroutines.flow.SharedFlow

interface SignUpViewModel {
    fun isValidFirstName(firstname: String): Boolean
    fun isValidLastName(lastname: String): Boolean
    fun isValidEmail(email: String): Boolean
    fun isValidPassword(password: String, confirmPass: String): Boolean

    fun signUp(user: User): String
}