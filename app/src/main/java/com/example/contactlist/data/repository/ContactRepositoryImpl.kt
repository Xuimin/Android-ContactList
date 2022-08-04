package com.example.contactlist.data.repository

import com.example.contactlist.data.database.ContactDao
import com.example.contactlist.data.model.Contact
import kotlinx.coroutines.delay

class ContactRepositoryImpl (private val dao: ContactDao): ContactRepository {

    override suspend fun getContacts(): List<Contact> {
        delay(1000)
        return dao.getContacts()
    }

    override suspend fun addContact(contact: Contact) {
        return dao.addContact(contact)
    }

    override suspend fun updateContact(id: Int, contact: Contact) {
        return dao.updateContact(contact.copy(id = id))
    }

    override suspend fun findContactById(id: Int): Contact? {
        return dao.findContactById(id)
    }

    override suspend fun deleteContact(id: Int) {
        return dao.deleteContact(id)
    }
}