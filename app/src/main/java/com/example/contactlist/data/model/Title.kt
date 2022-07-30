package com.example.contactlist.data.model

data class Title(
    val title: String
): BaseItem() {
    override fun getType(): String {
        return TITLE_TYPE
    }
}
