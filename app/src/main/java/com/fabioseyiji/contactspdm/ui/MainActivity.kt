package com.fabioseyiji.contactspdm.ui

import android.content.Context
import android.content.Intent
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

    private lateinit var paddarl: ActivityResultLauncher<Intent>

    //Data source
    private val contactList: MutableList<Contact> = mutableListOf()

    //Adapter
    class ContactAdapter(context: Context, contacts: List<Contact>) : ArrayAdapter<Contact>(context, android.R.layout.simple_list_item_1, contacts) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val contact = getItem(position)
            val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
            view.findViewById<TextView>(android.R.id.text1).text = contact?.name
            return view
        }
    }

    private val contactAdapter : ContactAdapter by lazy{
        ContactAdapter(this, contactList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        fillContacts()

        amb.contactsLv.adapter = contactAdapter

        amb.toolbarIn.toolbar.apply {
            setSupportActionBar(this)
        }

        paddarl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val lastId = if (contactList.isNotEmpty()) {
                    contactList.last().id
                } else {
                    1
                }

                result.data?.getBundleExtra("bundle")?.let {
                    val name = it.getString("name")
                    val address = it.getString("address")
                    val phone = it.getString("phone")
                    val email = it.getString("email")
                    if (!name.isNullOrBlank() && !phone.isNullOrBlank() && !email.isNullOrBlank() && !address.isNullOrBlank()) {
                        contactList.add(
                            Contact(lastId + 1, name, address, phone, email)
                        )
                        (contactAdapter as? ArrayAdapter<Contact>)?.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this, "Há dados obrigatórios que não foram preenchidos", Toast.LENGTH_SHORT).show()
                    }
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
                paddarl.launch(it)
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