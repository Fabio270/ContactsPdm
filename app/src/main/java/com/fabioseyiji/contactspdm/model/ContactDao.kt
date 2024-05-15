package com.fabioseyiji.contactspdm.model

interface ContactDao {
    fun createContact(contact: Contact): Int
    fun retrieveContacts(): MutableList<Contact>
    fun updateContact(contact: Contact): Int
    fun deleteContact(id: Int): Int
}