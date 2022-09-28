package com.example.contactlist.data.repository.signup

import android.content.ClipData
import androidx.lifecycle.ViewModel
import com.example.contactlist.data.model.User

interface SignUpRepository {
    suspend fun getUsers(): List<User>
    suspend fun addUser(user: User): User?
}