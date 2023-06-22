package com.example.medicine.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.medicine.R
import com.example.medicine.adapter.ChatAdapter
import com.example.medicine.databinding.FragmentChatBinding
import com.example.medicine.entities.Message
import com.example.medicine.repository.RepositoryChat
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters

   lateinit var binding:FragmentChatBinding
   private var param1: String? = null
    private var param2: String? = null
    lateinit var contexto: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_chat, container, false)
        return binding.root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto=context
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email=arguments?.getString("EMAIL_USER")?:""
        val chatListView=binding.chatListView
        RepositoryChat.refreshChat(email, chatListView,contexto)

        binding.sendButton.setOnClickListener {
        val content:String=binding.messageEditText.text.toString()
            sendMessage(email, chatListView, content)
        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMessage(
        email: String,
        chatListView: ListView,
        content: String
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection(email).get().addOnSuccessListener {
            val messages: MutableList<Message> = mutableListOf()
            for (document in it) {
                val sender = document.get("sender").toString()
                val receiver = document.get("receiver").toString()
                val content = document.get("content").toString()
                val message = Message(sender, receiver, content)
                messages.add(message)
            }
            val adapter = ChatAdapter(contexto, messages, email)
            chatListView.adapter = adapter
            val sender = email
            val receiver = messages.first().sender
            RepositoryChat.saveMessage(receiver, sender, content)
            RepositoryChat.refreshChat(email, chatListView,contexto)
        }


        binding.messageEditText.setText(" ")
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}