package com.example.medicine.repository

import android.content.Context
import android.widget.Toast
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.MedicalShift
import com.example.medicine.entities.User
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalTime

object UserRepository {
    private val users:MutableList<User> =mutableListOf()
    private val db = FirebaseFirestore.getInstance()
    fun loadUser() {
       users.clear()

        db.collection("users").get().addOnSuccessListener {
            for (document in it) {
                val dni = document.get("DNI").toString().toInt()
                val email = document.get("email").toString()
                val isDoctor = document.get("isDoctor").toString().toBoolean()
                val name= document.get("name").toString()
                val surname= document.get("surname").toString()
 val user=User(name, surname, dni, email, isDoctor)
                users.add(user)
            }
        }
    }

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
    fun UserForEmail(email:String):User{
       return users.first{it.email==email}
    }
}