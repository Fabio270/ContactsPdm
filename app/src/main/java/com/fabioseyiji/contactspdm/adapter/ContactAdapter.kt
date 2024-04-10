package com.fabioseyiji.contactspdm.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.getSystemService
import com.fabioseyiji.contactspdm.R
import com.fabioseyiji.contactspdm.databinding.TileContactBinding
import com.fabioseyiji.contactspdm.model.Contact

class ContactAdapter(context: Context, private val contactList: MutableList<Contact>) :
    ArrayAdapter<Contact>(context, R.layout.tile_contact, contactList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = contactList[position]
        val contactTileView = convertView
        if (contactTileView == null) {
            //contactTileView = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            //    .inflate(R.layout.tile_contact, parent, false)

            TileContactBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            ).root
        }

    }
}