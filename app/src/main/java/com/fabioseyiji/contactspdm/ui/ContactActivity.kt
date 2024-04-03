package com.fabioseyiji.contactspdm.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fabioseyiji.contactspdm.databinding.ActivityContactBinding

class ContactActivity: AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        acb.saveBt.setOnClickListener{
            val parametros = Bundle()
            val resultadoIntent = Intent()
            parametros.putString("name", acb.nameEt.text.toString())
            parametros.putString("address", acb.adressEt.text.toString())
            parametros.putString("phone", acb.phoneEt.text.toString())
            parametros.putString("email", acb.emailEt.text.toString())
            resultadoIntent.putExtra("bundle", parametros).also {
                setResult(RESULT_OK, it)
                finish()
            }
        }
    }
}