package com.example.contactlist.ui.contact.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.contactlist.databinding.FragmentAddEditContactBinding
import com.example.contactlist.ui.base.BaseFragment

abstract class BaseContactFragment: BaseFragment() {
    protected lateinit var binding: FragmentAddEditContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditContactBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}