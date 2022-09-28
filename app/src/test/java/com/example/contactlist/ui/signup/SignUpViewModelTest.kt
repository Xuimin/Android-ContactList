package com.example.contactlist.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.contactlist.MainCoroutineRule
import com.example.contactlist.data.model.User
import com.example.contactlist.data.repository.signup.SignUpRepositoryImpl
import com.example.contactlist.ui.signup.viewmodel.SignUpViewModelImpl
import com.jraska.livedata.test
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignUpViewModelTest {
    private lateinit var viewModel: SignUpViewModelImpl
    private val repo = SignUpRepositoryImpl()

    @Rule
    @JvmField
    val mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val taskCoroutineRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        viewModel = SignUpViewModelImpl(repo)
    }


    // Firstname
    @Test
    fun `isValidFirstName return false when firstname is empty`() { assertFalse(viewModel.isValidFirstName("")) }
    @Test
    fun `isValidFirstName return false when firstname contains number and special characters`() { assertFalse(viewModel.isValidFirstName("John12")) }
    @Test
    fun `isValidFirstName return true when firstname contains alphabets only`() { assertTrue(viewModel.isValidFirstName("John")) }


    // Lastname
    @Test
    fun `isValidLastName return false when lastname is empty`() { assertFalse(viewModel.isValidLastName("")) }
    @Test
    fun `isValidLastName return false when lastname contains number and special characters`() { assertFalse(viewModel.isValidLastName("Doe12")) }
    @Test
    fun `isValidLastName return true when lastname contains alphabets only`() { assertTrue(viewModel.isValidLastName("Doe")) }


    // Email
    @Test
    fun `isValidEmail return false when email is empty`() { assertFalse(viewModel.isValidEmail("")) }
    @Test
    fun `isValidEmail return false when missing prefix name`() { assertFalse(viewModel.isValidEmail("@gmail.com")) }
    @Test
    fun `isValidEmail return false when missing @ symbol`() { assertFalse(viewModel.isValidEmail("doegmail.com")) }
    @Test
    fun `isValidEmail return false when missing domain name`() { assertFalse(viewModel.isValidEmail("johndoe@")) }
    @Test
    fun `isValidEmail return true when matches the format`() { assertTrue(viewModel.isValidEmail("johndoe@gmail.com")) }


    // Password
    @Test
    fun `isValidPassword return false when password is empty`() { assertFalse(viewModel.isValidPassword("", "Johndoe12#")) }
    @Test
    fun `isValidPassword return false when confirm password is empty`() { assertFalse(viewModel.isValidPassword("Johndoe12#", "")) }
    @Test
    fun `isValidPassword return false when password and confirm password is not the same`() { assertFalse(viewModel.isValidPassword("Johndoe12#", "Janedoe12#")) }
    @Test
    fun `isValidPassword return true when password and confirm password matches the format and is the same`() { assertTrue(viewModel.isValidPassword("Johndoe12#", "Johndoe12#")) }


    // Sign up
    @Test
    fun `screen is empty when user is not sign up`() {
        viewModel.onCreateView()
        viewModel.emptyScreen.test().assertValue { it }
//        viewModel.users.test().assertValue { it.isEmpty() }
    }
    @Test
    fun `signUp successfully when all inputs matches the required format`() {
        val user = User(firstName = "John", lastName = "Doe", email = "johndoe@gmail.com", password = "Johndoe12#", confirmPassword = "Johndoe12#")
        assertEquals("success", viewModel.signUp(user))
    }
    @Test
    fun `user list is not empty when signUp successfully`() {
        val user = User(firstName = "John", lastName = "Doe", email = "johndoe@gmail.com", password = "Johndoe12#", confirmPassword = "Johndoe12#")

        viewModel.signUp(user)
        viewModel.onCreateView()
        viewModel.users.test().assertValue { it.isNotEmpty() }
    }
}