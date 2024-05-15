package com.fabioseyiji.contactspdm.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.fabioseyiji.contactspdm.R
import java.sql.SQLException

class ContactDaoSqlite(context: Context) : ContactDao {
    companion object Constant {
        private const val CONTACT_DATABASE_FILE = "contacts"
        private const val CONTACT_TABLE = "contact"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val ADDRESS_COLUMN = "address"
        private const val PHONE_COLUMN = "phone"
        private const val EMAIL_COLUMN = "email"

        private const val CREATE_CONTACT_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $CONTACT_TABLE ( " +
                    "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$NAME_COLUMN TEXT NOT NULL," +
                    "$ADDRESS_COLUMN TEXT NOT NULL," +
                    "$PHONE_COLUMN TEXT NOT NULL," +
                    "$EMAIL_COLUMN TEXT NOT NULL); "
    }

    private val contactSqliteDatabase: SQLiteDatabase = context.openOrCreateDatabase(
        CONTACT_DATABASE_FILE,
        MODE_PRIVATE,
        null
    )

    init {
        try {
            contactSqliteDatabase.execSQL(CREATE_CONTACT_TABLE_STATEMENT)
        } catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.message.toString())
        }

    }

    override fun createContact(contact: Contact) = contactSqliteDatabase.insert(
        CONTACT_TABLE,
        null,
        contact.toContentValues()
    ).toInt()


    override fun retrieveContacts(): MutableList<Contact> {
        val contactList = mutableListOf<Contact>()

        contactSqliteDatabase.rawQuery(
            "SELECT * FROM $CONTACT_TABLE ORDER BY $NAME_COLUMN",
            null)

    }

    override fun updateContact(contact: Contact) = contactSqliteDatabase.update(
        CONTACT_TABLE,
        contact.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(contact.id.toString())
    )

    override fun deleteContact(id: Int): Int {
        TODO("Not yet implemented")
    }

    private fun Contact.toContentValues() = ContentValues().apply {
        put(NAME_COLUMN, name)
        put(ADDRESS_COLUMN, address)
        put(PHONE_COLUMN, phone)
        put(EMAIL_COLUMN, email)
    }
}