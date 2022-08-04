package com.example.contactlist.ui.contact.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import com.example.contactlist.ui.contact.add.viewmodel.AddContactViewModelImpl
import com.example.contactlist.ui.contact.base.BaseContactFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactFragment : BaseContactFragment() {
    private val viewModel: AddContactViewModelImpl by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        onBindView()

//        viewModel.name.observe(viewLifecycleOwner) {
//            Log.d("AddContactFragment", "name: $it")
//        }

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