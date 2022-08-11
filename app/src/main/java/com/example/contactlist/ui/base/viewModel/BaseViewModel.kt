package com.example.contactlist.ui.base.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel: ViewModel() {
    open fun onViewCreated() {}

    private val _apiError: MutableSharedFlow<String> = MutableSharedFlow()
    val apiError: SharedFlow<String> = _apiError

    suspend fun <T> safeApiCall(apiCall: suspend () -> T?): T? {
        return try {
            apiCall()
        } catch (e: Exception) {
            _apiError.emit("Something went wrong")
            null
        }
    }
}