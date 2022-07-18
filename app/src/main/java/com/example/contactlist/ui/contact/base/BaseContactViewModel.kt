package com.example.contactlist.ui.contact.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseContactViewModel: ViewModel() {
    val name: MutableLiveData<String> = MutableLiveData()
    val phone: MutableLiveData<String> = MutableLiveData()
}