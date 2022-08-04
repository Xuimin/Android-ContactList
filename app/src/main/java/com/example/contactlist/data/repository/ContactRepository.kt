package com.example.contactlist.data.repository

import com.example.contactlist.data.model.Contact

interface ContactRepository {
    suspend fun getContacts(): List<Contact>
    suspend fun addContact(contact: Contact)
    suspend fun updateContact(id: Int, contact: Contact)
    suspend fun findContactById(id: Int): Contact?
    suspend fun deleteContact(id: Int)
}