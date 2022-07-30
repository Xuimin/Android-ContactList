package com.example.contactlist.ui.home

import androidx.lifecycle.*
import com.example.contactlist.data.model.BaseItem
import com.example.contactlist.data.model.Title
import com.example.contactlist.data.repository.ContactRepository
import java.lang.IllegalArgumentException

class HomeViewModel(private val contactRepository: ContactRepository): ViewModel() {
    private val _contacts: MutableLiveData<List<BaseItem>> = MutableLiveData()
    val contacts: LiveData<List<BaseItem>> = _contacts

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

    private fun getContacts() {
        val response = contactRepository.getContacts().sortedBy { it.name.toUpperCase() }
        val tempList: MutableList<BaseItem> = mutableListOf()
        val len = response.size

        if(len != 0) {
            val title = Title("${response[0].name[0].uppercase()}")
            tempList.add(title)
            tempList.add(response[0])
        }
        var i = 0
        while(i < len - 1) {
            if(response[i].name[0] == response[i + 1].name[0]) {
                tempList.add(response[i + 1])
            } else {
                val title = Title("${response[i + 1].name[0].uppercase()}")
                tempList.add(title)
                tempList.add(response[i + 1])
            }
            i++
        }

//        for(i in 1..len) {
//            if(i % 2 == 0) {
//                val title = Title("${response[i - 1].name[0]}")
//                tempList.add(title)
//            }
//            tempList.add(response[i - 1])
//        }
        _contacts.value = tempList
        emptyScreen.value = _contacts.value.isNullOrEmpty()
    }

    fun onDeleteClicked(id: Int) {
        contactRepository.deleteContact(id)
        refresh()
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
