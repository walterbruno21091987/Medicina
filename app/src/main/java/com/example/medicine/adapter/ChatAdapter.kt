package com.example.medicine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.medicine.R
import com.example.medicine.entities.Message

class ChatAdapter(context: Context, messages: List<Message>,val senderName: String) :
    ArrayAdapter<Message>(context, 0, messages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val message = getItem(position)
        val isSender = message?.sender == senderName

        if (itemView == null) {
            val layoutRes = if (isSender) R.layout.item_message_sender else R.layout.item_message_receiver
            itemView = LayoutInflater.from(context).inflate(layoutRes, parent, false)
        }

        val contentTextView: TextView = itemView!!.findViewById(R.id.contentTextView)
        if (isSender){val senderTextView:TextView=itemView!!.findViewById(R.id.senderTextView)
        senderTextView.setText(senderName)}else {
            val receiverTextView:TextView=itemView!!.findViewById(R.id.resiver_TextView)
            receiverTextView.setText(message!!.sender)
        }
        contentTextView.text = message?.content

        return itemView
    }
}
