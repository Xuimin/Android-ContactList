package com.example.contactlist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactlist.data.model.Contact
import com.example.contactlist.databinding.FragmentHomeBinding
import com.example.contactlist.ui.ContactAdapter
import com.example.contactlist.ui.base.BaseFragment
import com.example.contactlist.ui.core.ErrorHandler
import com.example.contactlist.ui.core.ErrorHandlerImpl
import com.example.contactlist.ui.home.viewmodel.HomeViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(), ErrorHandler by ErrorHandlerImpl() {
//    @Inject
//    lateinit var greeting: String

    lateinit var binding: FragmentHomeBinding
    lateinit var contactAdapter: ContactAdapter
    override val viewModel: HomeViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setUpAdapter()
        onBindView()
        setUpFragmentListener()
        setUpErrorHandler(view, viewModel, viewLifecycleOwner)

//        Log.d("Injection", greeting)
    }

    fun onBindView() {
        binding.srlContacts.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.refreshFinished.asLiveData().observe(viewLifecycleOwner) {
            binding.srlContacts.isRefreshing = false
        }

//        viewModel.finish.asLiveData().observe(viewLifecycleOwner) {
//            Toast.makeText(requireContext(), "Finished", Toast.LENGTH_SHORT).show()
//        }

        // recycleView
        viewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            contactAdapter.setModels(contacts)
        }

        // fab
        binding.fabAdd.setOnClickListener {
            navigateToAddContact()
        }

        binding.tvNavigateSignUp.setOnClickListener {
            navigateToSignUp()
        }
    }

    // recycleView
    fun setUpAdapter() {
        contactAdapter = ContactAdapter(emptyList())
        contactAdapter.listener = object: ContactAdapter.Listener {
            override fun onItemClicked(item: Contact) {
                navigateToEditContact(item.id!!)
            }

            override fun onDeleteClicked(item: Contact) {
                viewModel.onDeleteClicked(item.id!!)
            }
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvContacts.adapter = contactAdapter
        binding.rvContacts.layoutManager = layoutManager
    }

    fun navigateToAddContact() {
        val action = HomeFragmentDirections.actionHomeToAddContact()
        NavHostFragment.findNavController(this).navigate(action)
    }

    fun navigateToEditContact(id: Int) {
        val action = HomeFragmentDirections.actionHomeToEditContact(id)
        NavHostFragment.findNavController(this).navigate(action)
    }

    // refresh page/ refetch
    fun setUpFragmentListener() {
        setFragmentResultListener("add_contact_finished") {
                _, result ->
            if(result.getBoolean("refresh")) {
                viewModel.refresh()
            }
        }
        setFragmentResultListener("edit_contact_finished") {
                _, result ->
            if(result.getBoolean("refresh")) {
                viewModel.refresh()
            }
        }
    }

    fun navigateToSignUp() {
        val action = HomeFragmentDirections.actionHomeFragmentToSignUpFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}