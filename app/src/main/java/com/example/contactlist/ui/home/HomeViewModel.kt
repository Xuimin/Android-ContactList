package com.example.contactlist.ui.home

import androidx.lifecycle.*
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class HomeViewModel(private val contactRepository: ContactRepository): ViewModel() {
    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> = _contacts

    val emptyScreen: MutableLiveData<Boolean> = MutableLiveData(false)
//    val _finish: MutableSharedFlow<Unit> = MutableSharedFlow()
//    val finish: SharedFlow<Unit> = _finish

    init {
        getContacts()
    }

//    fun onViewCreated() {
//        viewModelScope.launch {
//            _finish.emit(Unit)
//        }
//    }

    fun getContacts() {
        val response = contactRepository.getContacts()
        _contacts.value = response
        emptyScreen.value = _contacts.value.isNullOrEmpty()
    }

    fun refresh() {
        getContacts()
    }

    class Provider(private val repository: ContactRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Invalid ViewModel")
        }
    }
}