package com.example.contactlist.ui.core

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.example.contactlist.R
import com.example.contactlist.ui.core.viewmodel.SafeApiCall
import com.google.android.material.snackbar.Snackbar

class ErrorHandlerImpl: ErrorHandler {
    override fun setUpErrorHandler(view: View, safeApiViewModel: SafeApiCall, lifecycleOwner: LifecycleOwner) {
        safeApiViewModel.apiError2.asLiveData().observe(lifecycleOwner) {
            val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
            snackbar.setBackgroundTint(
                ContextCompat.getColor(view.context, R.color.red)
            )
            snackbar.show()
        }
        Log.d("ErrorHandler", "I'm inside errorHandler")
    }
}