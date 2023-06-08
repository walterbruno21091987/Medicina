package com.example.medicine.repository

import com.example.medicine.entities.Doctor
import com.example.medicine.entities.Specialty
import com.google.firebase.firestore.FirebaseFirestore

object DoctorRepository {


    private val doctors:MutableList<Doctor> =mutableListOf()


    fun getDoctorForEmail(email: String): Doctor {
      return doctors.first { it.email == email }
    }

fun addDoctor(doctor:Doctor):Boolean{
    return doctors.add(doctor)
}




}