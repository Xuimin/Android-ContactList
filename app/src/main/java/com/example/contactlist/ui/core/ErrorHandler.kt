package com.example.contactlist.ui.core

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.contactlist.ui.core.viewmodel.SafeApiCall

interface ErrorHandler {
    fun setUpErrorHandler(view: View, safeApiViewModel: SafeApiCall, lifecycleOwner: LifecycleOwner)
}