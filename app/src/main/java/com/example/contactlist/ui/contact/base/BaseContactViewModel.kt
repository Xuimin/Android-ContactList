package com.example.contactlist.ui.contact.base

import androidx.lifecycle.MutableLiveData
import com.example.contactlist.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseContactViewModel: BaseViewModel() {
    // val name: MutableStateFlow = MutableStateFlow("")
    val name: MutableLiveData<String> = MutableLiveData()
    val phone: MutableLiveData<String> = MutableLiveData()

    protected val _finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val finish: SharedFlow<Unit> = _finish

    protected val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: SharedFlow<String> = _error
}