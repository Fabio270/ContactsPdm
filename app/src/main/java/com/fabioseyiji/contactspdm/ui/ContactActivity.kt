package com.fabioseyiji.contactspdm.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fabioseyiji.contactspdm.databinding.ActivityContactBinding
import com.fabioseyiji.contactspdm.model.Constants.Companion.EXTRA_CONTACT
import com.fabioseyiji.contactspdm.model.Constants.Companion.EXTRA_VIEW_CONTACT
import com.fabioseyiji.contactspdm.model.Contact

class ContactActivity : AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)
        acb.toolbarIn.toolbar.apply {
            subtitle = this@ContactActivity.javaClass.simpleName
            setSupportActionBar(this)
        }

        val receveidContact =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_CONTACT", Contact::class.java)
        } else {
            intent.getParcelableExtra("EXTRA_CONTACT")
        }

        with(acb) {
            receveidContact?.let {
                nameEt.setText(receveidContact.name)
                adressEt.setText(receveidContact.address)
                phoneEt.setText(receveidContact.phone)
                emailEt.setText(receveidContact.email)
                if (intent.getBooleanExtra(EXTRA_VIEW_CONTACT, false)){
                    nameEt.isEnabled = false
                    adressEt.isEnabled = false
                    phoneEt.isEnabled = false
                    emailEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
            }
            saveBt.setOnClickListener {
                Contact(
                    id = receveidContact?.id?:hashCode(),
                    name = nameEt.text.toString(),
                    address = adressEt.text.toString(),
                    phone = phoneEt.text.toString(),
                    email = emailEt.text.toString()
                ).let { contact ->
                    Intent().apply {
                        putExtra(EXTRA_CONTACT, contact)
                        setResult(RESULT_OK, this)
                        finish()
                    }
                }
            }
        }
    }


}