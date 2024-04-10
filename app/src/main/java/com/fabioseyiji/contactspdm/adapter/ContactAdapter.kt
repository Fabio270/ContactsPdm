package com.fabioseyiji.contactspdm.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.fabioseyiji.contactspdm.R
import com.fabioseyiji.contactspdm.databinding.TileContactBinding
import com.fabioseyiji.contactspdm.model.Contact

class ContactAdapter(context: Context, private val contactList: MutableList<Contact>) :
    ArrayAdapter<Contact>(context, R.layout.tile_contact, contactList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = contactList[position]
        var contactTileView = convertView
        if (contactTileView == null) {
            contactTileView = TileContactBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            ).root
            val tileContactHolder = TileContactHolder(
                contactTileView.findViewById(R.id.nameTv),
                contactTileView.findViewById(R.id.emailTv)
            )
            contactTileView.tag = tileContactHolder
        }

        (contactTileView.tag as TileContactHolder).apply {
            nameTv.text = contact.name
            emailTv.text = contact.email
        }
        return contactTileView!!

    }

    private data class TileContactHolder(val nameTv: TextView, val emailTv: TextView)
}