package com.example.contactlist.ui.contact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.contactlist.MainCoroutineRule
import com.example.contactlist.data.repository.contact.ContactRepository
import com.example.contactlist.ui.home.viewmodel.HomeViewModelImpl
import com.jraska.livedata.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

class HomeContactViewModelTest {

    private lateinit var homeViewModel: HomeViewModelImpl
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
            homeViewModel = HomeViewModelImpl(repo)
        }
    }

    @Test
    fun `Contact should be deleted`() {
        homeViewModel.onDeleteClicked(1)
        homeViewModel.getAllContact()
        homeViewModel.contacts.test().assertValue { it.isNullOrEmpty() }
    }

    @Test
    fun `Contact should return empty screen when there is no contacts`() {
        mainCoroutineRule.runBlockingTest {
            homeViewModel.getAllContact()
            homeViewModel.emptyScreen.test().assertValue { it }
        }
    }
}