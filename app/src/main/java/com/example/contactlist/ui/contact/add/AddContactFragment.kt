package com.example.contactlist.ui.contact.add

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.ui.contact.base.BaseContactFragment

class AddContactFragment : BaseContactFragment() {
    private val viewModel: AddContactViewModel by viewModels {
        AddContactViewModel.Provider(ContactRepository.contactRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        onBindView()

//        viewModel.name.observe(viewLifecycleOwner) {
//            Log.d("AddContactFragment", "name: $it")
//        }

//        binding.etName.addTextChangedListener {
//            viewModel.name.value = it.toString()
//        }
//
//        binding.etPhone.addTextChangedListener {
//            viewModel.phone.value = it.toString()
//        }
    }

    fun onBindView() {
        binding.btnSave.setOnClickListener {
            viewModel.save()
        }

        viewModel.error.asLiveData().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.finish.asLiveData().observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            setFragmentResult("add_contact_finished", bundle)
            NavHostFragment.findNavController(this).popBackStack() // to navigate
        }
    }
}