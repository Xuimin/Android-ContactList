package com.example.contactlist.ui.contact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.contactlist.MainCoroutineRule
import com.example.contactlist.data.repository.contact.ContactRepository
import com.example.contactlist.ui.contact.edit.viewmodel.EditContactViewModelImpl
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

class EditContactViewModelTest {

    private lateinit var editViewModel: EditContactViewModelImpl
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
            editViewModel = EditContactViewModelImpl(repo)
        }
    }

    @Test
    fun `Contact should be successfully updated`() {
        editViewModel.name.value = "Jane Doe"
        editViewModel.phone.value = "0123456788"
        editViewModel.update(1)
        TestCase.assertEquals("Working", editViewModel.success.value)
    }
}