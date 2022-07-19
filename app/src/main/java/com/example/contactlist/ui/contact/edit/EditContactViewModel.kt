package com.example.contactlist.ui.contact.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.ui.contact.base.BaseContactViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class EditContactViewModel(private val repository: ContactRepository): BaseContactViewModel() {
    fun onViewCreated(id: Int) {
        val response = repository.findContactById(id)
        response?.let {
            name.value = it.name
            phone.value = it.phone
        }
    }

    fun update(id: Int) {
        if(name.value.isNullOrEmpty() || phone.value.isNullOrEmpty()) {
            viewModelScope.launch {
                _error.emit("Something went wrong")
            }
        } else {
            val contact = Contact(id = id, name = name.value!!, phone = phone.value!!)
            repository.updateContact(id, contact)
            viewModelScope.launch {
                _finish.emit(Unit)
            }
        }
    }

    class Provider(private val repository: ContactRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(EditContactViewModel::class.java)) {
                return EditContactViewModel(repository) as T
            }
            throw IllegalArgumentException("Invalid viewmodel")
        }
    }
}