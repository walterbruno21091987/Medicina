package com.example.medicine.repository

import android.content.Context
import android.os.Build
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.medicine.adapter.ChatAdapter
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.Message
import com.example.medicine.entities.Specialty
import com.google.firebase.firestore.FirebaseFirestore

object DoctorRepository {


    private val doctors:MutableList<Doctor> =mutableListOf()

   fun loadDataDoctor() {
       doctors.clear()
       val db = FirebaseFirestore.getInstance()
        db.collection("Doctor").get().addOnSuccessListener {
            var especialidad = Specialty.CONSULTA_CLINICA
            for (document in it) {
                when (document.get("especialidad").toString()) {
                    "CARDIOLOGIA" -> {
                        especialidad = Specialty.CARDIOLOGIA
                    }

                    "ODONTOLOGIA" -> {
                        especialidad = Specialty.ODONTOLOGIA
                    }

                    "TRAUMATOLOGIA" -> {
                        especialidad = Specialty.TRAUMATOLOGIA
                    }

                    "OFTALMOLOGIA" -> {
                        especialidad = Specialty.OFTALMOLOGIA
                    }

                    "PSICOLOGIA" -> {
                        especialidad = Specialty.PSICOLOGIA
                    }

                    "RADIOLOGIA" -> {
                        especialidad = Specialty.RADIOLOGIA
                    }

                    "DERMATOLOGIA" -> {
                        especialidad = Specialty.DERMATOLOGIA
                    }

                    "PEDIATRIA" -> {
                        especialidad = Specialty.PEDIATRIA
                    }

                    "CONSULTA CLINICA" -> {
                        especialidad = Specialty.CONSULTA_CLINICA
                    }
                }

                val doctor = Doctor(
                    document.get("name").toString(),
                    document.get("surname").toString(),
                    document.get("dni").toString().toInt(),
                    document.get("email").toString(),
                    especialidad,document.get("chatavailable").toString().toBoolean()
                )
                doctors.add(doctor)
            }
        }
    }
    fun getDoctorForEmail(email: String): Doctor {
      return doctors.first { it.email == email }
    }
    fun getDoctorForChat(): Doctor{
        var doctor: Doctor
        do {
            doctor = doctors.filter { it.specialty == Specialty.CONSULTA_CLINICA }.random()
        } while (doctor.chatEnable == false)
        return doctor
    }
fun addDoctor(doctor:Doctor):Boolean{
    return doctors.add(doctor)
}






}