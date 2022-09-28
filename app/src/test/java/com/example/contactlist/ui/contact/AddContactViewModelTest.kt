package com.example.contactlist.ui.contact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.contactlist.MainCoroutineRule
import com.example.contactlist.data.repository.contact.ContactRepository
import com.example.contactlist.ui.contact.add.viewmodel.AddContactViewModelImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

class AddContactViewModelTest {

    private lateinit var addViewModel: AddContactViewModelImpl
    private val repo = mock<ContactRepository>()

    @Rule
    @JvmField
    val mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val taskCoroutineRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mainCoroutineRule.runBlockingTest {
            addViewModel = AddContactViewModelImpl(repo)
        }
    }

    @Test
    fun `Contact should be successfully added`() {
        addViewModel.name.value = "Jane Doe"
        addViewModel.phone.value = "0123456788"
        addViewModel.save()
        assertEquals("Working", addViewModel.success.value)
    }
}