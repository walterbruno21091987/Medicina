package com.example.medicine.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.medicine.adapter.ChatAdapter
import com.example.medicine.entities.Message
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime

object RepositoryChat {
    private var messages : MutableList<Message> = mutableListOf()
  @SuppressLint("StaticFieldLeak")
  private val db=FirebaseFirestore.getInstance()
fun loadChat(email:String){
messages.clear()
    db.collection(email).get().addOnSuccessListener {
        for (document in it) {
          val sender=document.get("sender").toString()
            val receiver=document.get("receiver").toString()
            val content=document.get("content").toString()
            val message=Message(sender,receiver,content)
            messages.add(message)
        }
    }
}

fun get():List<Message>{
    return messages
}
@RequiresApi(Build.VERSION_CODES.O)
fun saveMessage(receiver: String, sender: String, content: String){
val date=LocalDateTime.now().toString()
    db.collection(sender).document(date).set(
        hashMapOf("sender" to sender,"receiver" to receiver,"content" to content)
    )
    db.collection(receiver).document(date).set(
        hashMapOf("sender" to sender,"receiver" to receiver,"content" to content)
    )
}
    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshChat(email: String, chatListView: ListView, contexto: Context) {
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
            if(messages.isEmpty()){
                val doctor=DoctorRepository.getDoctorForChat()
                val sender=doctor.email
                val receiver=email
                val content=" HOLA SOY EL DOCTOR ${doctor.name}  ${ doctor.surname} EN QUE PUEDO AYUDARTE ?"
               saveMessage(receiver, sender, content)
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
                }}else{
                val adapter = ChatAdapter(contexto, messages, email)
                chatListView.adapter = adapter

            }

        }
    }
}