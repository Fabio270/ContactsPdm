package com.fabioseyiji.contactspdm.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.fabioseyiji.contactspdm.R
import com.fabioseyiji.contactspdm.databinding.ActivityMainBinding
import com.fabioseyiji.contactspdm.model.Contact

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    //Data source
    private val contactList: MutableList<Contact> = mutableListOf()

    private val contactAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            contactList.map { contact ->
                contact.toString()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        fillContacts()

        amb.contactsLv.adapter = contactAdapter

        amb.toolbarIn.toolbar.apply {
            setSupportActionBar(this)
        }

        carl = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == RESULT_OK){
                val contact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    result.data?.getParcelableExtra("EXTRA_CONTACT", Contact::class.java)
                }
                else{
                    result.data?.getParcelableExtra("EXTRA_CONTACT")
                }
                if (contact != null){
                    contactList.add(contact)
                    contactAdapter.add(contact.toString())
                    contactAdapter.notifyDataSetChanged()
                }
        }
    }
}

override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.viewMi) {
        Intent(this, ContactActivity::class.java).also {
            carl.launch(it)
        }
    }
    return true
}

private fun fillContacts() {
    for (i in 1..10) {
        contactList.add(
            Contact(
                i,
                "Name $i",
                "Address $i",
                "Phone $i",
                "Email $i"
            )
        )
    }
}
}