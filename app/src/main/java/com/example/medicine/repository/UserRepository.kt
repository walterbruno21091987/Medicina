package com.example.medicine.repository

import com.example.medicine.entities.User
import com.example.medicine.entities.Affiliate
import com.google.firebase.firestore.FirebaseFirestore

object UserRepository {
    private val users:MutableList<User> =mutableListOf()
    fun add(user: User, password:String): Boolean {
        addlogin(user, password)
        createDataUser(user)

        return true

    }

    private fun createDataUser(user: User) {
        val dbRegister = FirebaseFirestore.getInstance()
        val userEntry = hashMapOf(
            "DNI" to user.dni.toString(),
            "isDoctor" to user.isDoctor,
            "name" to user.name,
            "surname" to user.surname
        )
        dbRegister.collection("users").document(user.email).set(userEntry)
        }



    private fun addlogin(user: User, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
    }



}

