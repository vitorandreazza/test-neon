package com.example.neontest

import com.example.neontest.data.model.Contact
import com.example.neontest.data.model.Transfer
import com.example.neontest.ui.transfer.ContactItem
import java.util.*

val CONTACT_ID1 = 1L
val CONTACT_ID2 = 2L
val TRANSFER1 = Transfer(1, CONTACT_ID1, 20f, "token1", Date())
val TRANSFER2 = Transfer(2, CONTACT_ID2, 10f, "token2", Date())
val CONTACT1 = Contact(CONTACT_ID1, "name", "111111", "url1")
val CONTACT2 = Contact(CONTACT_ID2, "name2", "22222", "url2")
val CONTACT_ITEM1 = ContactItem(
    CONTACT1.id,
    CONTACT1.name,
    CONTACT1.phone,
    CONTACT1.avatarUrl,
    TRANSFER1.value
)
val CONTACT_ITEM2 = ContactItem(
    CONTACT2.id,
    CONTACT2.name,
    CONTACT2.phone,
    CONTACT2.avatarUrl,
    TRANSFER1.value
)

val TRANSFERS = arrayListOf(TRANSFER1, TRANSFER2)
val CONTACTS = arrayListOf(CONTACT1, CONTACT2)
val CONTACT_ITEMS = arrayListOf(CONTACT_ITEM1, CONTACT_ITEM2)
