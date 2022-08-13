package com.example.contactlist.ui.contact.add.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.data.repository.ContactRepositoryImpl
import com.example.contactlist.ui.base.viewModel.BaseViewModel
import com.example.contactlist.ui.contact.base.BaseContactViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModelImpl @Inject constructor(val repository: ContactRepository): BaseContactViewModel(), AddContactViewModel {

    override fun save() {
        viewModelScope.launch {
            if(name.value.isNullOrEmpty() || phone.value.isNullOrEmpty()) {
                _error.emit("Please enter both name and phone correctly") // send/give a message
            } else {
                val contact = Contact(name = name.value!!, phone = phone.value!!)
                _loading.emit(true)
                repository.addContact(contact)
                _loading.emit(false)
                _finish.emit(Unit)
            }
        }
    }
}