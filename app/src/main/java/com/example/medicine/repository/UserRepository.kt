package com.example.medicine.repository

import android.content.Context
import android.widget.Toast
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.User
import com.google.firebase.firestore.FirebaseFirestore

object UserRepository {
    private val users:MutableList<User> =mutableListOf()


   fun createDataUser(user: User,context:Context) {
       val dbRegister = FirebaseFirestore.getInstance()

        val userEntry = hashMapOf(
            "DNI" to user.dni,
            "isDoctor" to user.isDoctor,
            "name" to user.name,
            "surname" to user.surname,
            "email" to user.email
        )
        dbRegister.collection("users").document(user.email).set(userEntry).addOnCanceledListener {
            Toast.makeText(context,"NO SE PUDO AGREGAR LOS DATOS A LA BASE DE DATOS", Toast.LENGTH_LONG).show()
        }
    }
}