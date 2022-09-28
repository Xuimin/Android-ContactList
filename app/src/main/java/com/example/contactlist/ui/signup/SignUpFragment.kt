package com.example.contactlist.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.contactlist.data.repository.signup.SignUpRepositoryImpl
import com.example.contactlist.databinding.FragmentSignUpBinding
import com.example.contactlist.ui.signup.viewmodel.SignUpViewModelImpl

class SignUpFragment : Fragment() {

    val viewModel: SignUpViewModelImpl by viewModels {
        SignUpViewModelImpl.Provider(SignUpRepositoryImpl())
    }
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        binding.btnSignUp.setOnClickListener {
            val firstname = viewModel.isValidFirstName(binding.etFirstname.text.toString())
            val lastname = viewModel.isValidLastName(binding.etLastname.text.toString())
            val email = viewModel.isValidEmail(binding.etEmail.text.toString())
            val password = viewModel.isValidPassword(binding.etPassword.text.toString(), binding.etConfirmPassword.text.toString())

            if(!firstname || !lastname || !email || !password) Toast.makeText(requireContext(), "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(requireContext(), "Successfully sign up!", Toast.LENGTH_SHORT).show()
                val action = SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                NavHostFragment.findNavController(this).navigate(action)
            }
        }
    }
}