package com.example.contactlist.ui.contact.edit.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.data.repository.ContactRepositoryImpl
import com.example.contactlist.ui.contact.base.BaseContactViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditContactViewModelImpl @Inject constructor(val repository: ContactRepository): BaseContactViewModel(), EditContactViewModel {
    override fun onViewCreated(id: Int) {
        viewModelScope.launch {
            val response = repository.findContactById(id)
            response?.let {
                name.value = it.name
                phone.value = it.phone
            }
        }
    }

    override fun update(id: Int) {
        viewModelScope.launch {
            if(name.value.isNullOrEmpty() || phone.value.isNullOrEmpty()) {
                _error.emit("Something went wrong")
            } else {
                val contact = Contact(id = id, name = name.value!!, phone = phone.value!!)
                repository.updateContact(id, contact)
                _finish.emit(Unit)
            }
        }
    }
}