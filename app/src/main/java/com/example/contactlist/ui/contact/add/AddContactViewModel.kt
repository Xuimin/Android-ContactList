package com.example.contactlist.ui.contact.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.ui.contact.base.BaseContactViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AddContactViewModel(private val repository: ContactRepository): BaseContactViewModel() {

    fun save() {
        if(name.value.isNullOrEmpty() || phone.value.isNullOrEmpty()) {
            viewModelScope.launch {
                _error.emit("Please enter both name and phone correctly") // send/give a message
            }
        } else {
            val contact = Contact(name = name.value!!, phone = phone.value!!)
            repository.addContact(contact)
            viewModelScope.launch {
                _finish.emit(Unit)
            }
        }
    }

    class Provider(private val repository: ContactRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(AddContactViewModel::class.java)) {
                return AddContactViewModel(repository) as T
            }
            throw IllegalArgumentException("ViewModel is not valid")
        }
    }
}