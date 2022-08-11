package com.example.contactlist.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contactlist.data.model.BaseItem
import com.example.contactlist.data.model.Title
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.ui.base.viewModel.BaseViewModel
import com.example.contactlist.ui.core.viewmodel.SafeApiCall
import com.example.contactlist.ui.core.viewmodel.SafeApiCallImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(val contactRepository: ContactRepository): HomeViewModel, BaseViewModel(), SafeApiCall by SafeApiCallImpl() {
    private val _contacts: MutableLiveData<List<BaseItem>> = MutableLiveData()
    override val contacts: LiveData<List<BaseItem>> = _contacts

    override val emptyScreen: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _refreshFinished: MutableSharedFlow<Unit> = MutableSharedFlow()
    override val refreshFinished: SharedFlow<Unit> = _refreshFinished

    override fun onViewCreated() {
        super.onViewCreated()
        getContacts()
    }

//    init {
//        getContacts()
//    }

//    fun onViewCreated() {
//        viewModelScope.launch {
//            _finish.emit(Unit)
//        }
//    }

    private fun getContacts() {
        viewModelScope.launch {
//            val response = contactRepository.getContacts().sortedBy { it.name.uppercase() }
            val response = safeApiCall2 { contactRepository.getContacts() }
            response?.let {
                _refreshFinished.emit(Unit)
                val tempList: MutableList<BaseItem> = mutableListOf()
                val len = response.size

                if (len != 0) {
                    val title = Title("${response[0].name[0].uppercase()}")
                    tempList.add(title)
                    tempList.add(response[0])
                }
                var i = 0
                while (i < len - 1) {
                    if (response[i].name[0] == response[i + 1].name[0]) {
                        tempList.add(response[i + 1])
                    } else {
                        val title = Title("${response[i + 1].name[0].uppercase()}")
                        tempList.add(title)
                        tempList.add(response[i + 1])
                    }
                    i++
                }
                _contacts.value = tempList
                emptyScreen.value = _contacts.value.isNullOrEmpty()

//        for(i in 1..len) {
//            if(i % 2 == 0) {
//                val title = Title("${response[i - 1].name[0]}")
//                tempList.add(title)
//            }
//            tempList.add(response[i - 1])
//        }
            }
        }
    }

    override fun onDeleteClicked(id: Int) {
        viewModelScope.launch {
            contactRepository.deleteContact(id)
            refresh()
        }
    }

    override fun refresh() {
        getContacts()
    }
}
