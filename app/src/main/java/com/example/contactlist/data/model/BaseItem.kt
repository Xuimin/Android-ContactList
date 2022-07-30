package com.example.contactlist.data.model

abstract class BaseItem {
    abstract fun getType(): String

    companion object {
        const val TITLE_TYPE = "title"
        const val ITEM_TYPE = "item"
    }
}