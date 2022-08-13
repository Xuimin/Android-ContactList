package com.example.contactlist.ui.base

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.contactlist.R
import com.example.contactlist.databinding.FullScreenLoaderBinding
import com.example.contactlist.ui.base.viewModel.BaseViewModel
import com.example.contactlist.ui.contact.add.viewmodel.AddContactViewModelImpl
import com.example.contactlist.ui.contact.edit.viewmodel.EditContactViewModelImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

abstract class BaseFragment : Fragment() {
    abstract val viewModel: BaseViewModel
    lateinit var loader: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.onViewCreated()
        }

        loader = Dialog(requireActivity(), R.style.loader)
        val view = FullScreenLoaderBinding.inflate(layoutInflater, null, false)
        loader.setContentView(view.root)
        loader.setCancelable(false)
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
        viewModel.loading.asLiveData().observe(viewLifecycleOwner) {
            if(it) {
                loader.show()
            } else {
                loader.hide()
            }
        }
    }
}