package com.example.contactlist.data.repository.signup

import com.example.contactlist.data.model.User

class SignUpRepositoryImpl: SignUpRepository {
    private var counter = 1
    val contacts: MutableMap<Int, User> = mutableMapOf()

    override suspend fun getUsers(): List<User> {
        return contacts.values.toList()
    }

    override suspend fun addUser(user: User): User? {
        counter++
        contacts[counter] = user.copy(id = counter)
        return contacts[counter]
    }
}