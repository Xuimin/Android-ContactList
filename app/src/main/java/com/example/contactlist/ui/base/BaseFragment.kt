package com.example.contactlist.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.contactlist.R
import com.example.contactlist.ui.base.viewModel.BaseViewModel
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {
    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.onViewCreated()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apiError.asLiveData().observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
            snackbar.setBackgroundTint(
                ContextCompat.getColor(requireContext(), R.color.red)
            )
            snackbar.show()
        }
    }
}