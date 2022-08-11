package com.example.contactlist.ui.core.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class SafeApiCallImpl: SafeApiCall {
    private val _apiError: MutableSharedFlow<String> = MutableSharedFlow()
    override val apiError2: SharedFlow<String> = _apiError

    override suspend fun <T> safeApiCall2(apiCall: suspend () -> T?): T? {
        return try {
            apiCall()
        } catch (e: Exception) {
            _apiError.emit("Something went wrong from delegation")
            null
        }
    }
}