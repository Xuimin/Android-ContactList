package com.example.contactlist.data.repository

import com.example.contactlist.data.database.ContactDao
import com.example.contactlist.data.model.Contact
import kotlinx.coroutines.delay

class ContactRepositoryImpl (private val dao: ContactDao): ContactRepository {

    override suspend fun getContacts(): List<Contact> {
//        throw Exception("Error")
        delay(3000)
        return dao.getContacts()
    }

    override suspend fun addContact(contact: Contact) {
        delay(2000)
        return dao.addContact(contact)
    }

    override suspend fun updateContact(id: Int, contact: Contact) {
        delay(2500)
        return dao.updateContact(contact.copy(id = id))
    }

    override suspend fun findContactById(id: Int): Contact? {
        delay(2300)
        return dao.findContactById(id)
    }

    override suspend fun deleteContact(id: Int) {
        delay(2000)
        return dao.deleteContact(id)
    }
}