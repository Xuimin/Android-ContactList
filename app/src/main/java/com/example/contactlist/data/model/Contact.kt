package com.example.contactlist.data.model

// have to be a data class for .copy() function
data class Contact (
    val id: Int? = null,
    val name: String,
    val phone: String
    ) {
}