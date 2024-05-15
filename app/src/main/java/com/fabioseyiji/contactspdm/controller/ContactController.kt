package com.fabioseyiji.contactspdm.controller

import com.fabioseyiji.contactspdm.model.Contact
import com.fabioseyiji.contactspdm.model.ContactDao
import com.fabioseyiji.contactspdm.model.ContactDaoSqlite
import com.fabioseyiji.contactspdm.ui.MainActivity

class ContactController(mainActivity: MainActivity) {
    private val contactDaoImpl: ContactDao = ContactDaoSqlite(mainActivity)

    fun insertContact(contact: Contact) = contactDaoImpl.createContact(contact)

    fun getContacts() = contactDaoImpl.retrieveContacts()

    fun editContact(contact: Contact) = contactDaoImpl.updateContact(contact)

    fun removeContact(id: Int) = contactDaoImpl.deleteContact(id)
}