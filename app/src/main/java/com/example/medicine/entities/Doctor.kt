package com.example.medicine.entities

import com.google.firebase.firestore.FirebaseFirestore

class Doctor (name:String, surname:String, dni:Int, email:String, val specialty: Specialty, val chatEnable: Boolean =true, isDoctor:Boolean=true) :User(name,surname,dni,email,isDoctor) {
    fun changeChatEnable(){
        val db = FirebaseFirestore.getInstance()
        db.collection("Doctor").document(email).set(
            hashMapOf("chatavailable" to false,
                "dni" to dni,
                "email" to email,
                "especialidad" to specialty.toString(),
                "isDoctor" to true,
                "name" to name,
                "surname" to surname )
        )}
}