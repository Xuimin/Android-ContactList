package com.example.contactlist.ui.signup.viewmodel

import androidx.lifecycle.*
import com.example.contactlist.data.model.User
import com.example.contactlist.data.repository.signup.SignUpRepositoryImpl
import com.example.contactlist.ui.base.viewModel.BaseViewModel
import com.example.contactlist.ui.core.viewmodel.SafeApiCall
import com.example.contactlist.ui.core.viewmodel.SafeApiCallImpl
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignUpViewModelImpl(private val repository: SignUpRepositoryImpl): SignUpViewModel, BaseViewModel(), SafeApiCall by SafeApiCallImpl() {

    private val _emptyScreen: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = true
    }
    val emptyScreen: LiveData<Boolean> = _emptyScreen

    private val _users: MutableLiveData<List<User>> = MutableLiveData()
    val users: LiveData<List<User>> = _users

    fun onCreateView() {
        viewModelScope.launch {
            val res = repository.getUsers()
            _users.value = res
            _emptyScreen.value = res.isEmpty()
        }
    }

    // firstname and lastname conditions
    fun validateName(input: String): Boolean {
        val PATTERN: Pattern = Pattern.compile("[a-zA-Z]+")
        return PATTERN.matcher(input).matches()
    }

    // email conditions
    fun validateEmail(input: String): Boolean {
        val PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return PATTERN.matcher(input).matches()
    }

    // password conditions
    fun validatePass(input: String): Boolean {
        val PATTERN: Pattern = Pattern.compile("^" + "(?=.*[@#$%^&+=])" +  "(?=\\S+$)" + "(?=.*[A-Z])" + ".{6,}" + "$")
        return PATTERN.matcher(input).matches()
    }

    override fun isValidFirstName(firstname: String): Boolean {
        return if(firstname.isNullOrEmpty()) false
        else validateName(firstname)
    }

    override fun isValidLastName(lastname: String): Boolean {
        return if(lastname.isNullOrEmpty()) false
        else validateName(lastname)
    }

    override fun isValidEmail(email: String): Boolean {
        return if(email.isNullOrEmpty()) false
        else validateEmail(email)
    }

    override fun isValidPassword(password: String, confirmPass: String): Boolean {
        return if(password != confirmPass) false
        else if(password.isNullOrEmpty() || confirmPass.isNullOrEmpty()) false
        else validatePass(password)
    }

    override fun signUp(user: User): String {
        viewModelScope.launch {
            repository.addUser(user)
        }

        var status = ""

        status = if(user.firstName.isNullOrEmpty() || !validateName(user.firstName)) "fail firstname"
        else if(user.lastName.isNullOrEmpty() || !validateName(user.lastName)) "fail lastname"
        else if(user.email.isNullOrEmpty() || !validateEmail(user.email)) "fail email"
        else if(user.password.isNullOrEmpty() || !validatePass(user.password)) "fail password"
        else if(user.password != user.confirmPassword) "password not match"
        else "success"

        return status
    }

    class Provider(private val repository: SignUpRepositoryImpl): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModelImpl(repository) as T
        }
    }
}