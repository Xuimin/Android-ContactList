package com.example.contactlist.data.repository

import com.example.contactlist.data.model.Contact

class ContactRepository {
    var counter = 1
    val contacts: MutableMap<Int, Contact> = mutableMapOf(
        0 to Contact(id = 0, name = "Hello", phone = "1234"),
        1 to Contact(id = 1, name = "World!", phone = "5678")
    )

    fun getContacts(): List<Contact> {
        return contacts.values.toList()
    }

    fun addContact(contact: Contact): Contact? {
        counter++
        contacts[counter] = contact.copy(id = counter)

        return contacts[counter]
    }

    fun updateContact(id: Int, contact: Contact): Contact? {
        contacts[id] = contact
        return contacts[id]
    }

    fun findContactById(id: Int): Contact? {
        return contacts[id]
    }

    fun deleteContact(id: Int): Contact? {
        return contacts.remove(id)
    }

    companion object {
        val contactRepository = ContactRepository()
    }
}