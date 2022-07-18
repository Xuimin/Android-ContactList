package com.example.contactlist.data.model

class Contact (
    val id: Int? = null,
    val name: String,
    val phone: String
    ) {
    fun copy(id: Int): Contact {
        return Contact(id, name, phone)
    }
}