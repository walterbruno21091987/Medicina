package com.example.medicine.repository

import android.content.Context
import android.widget.Toast
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.Specialty
import com.example.medicine.exception.NoMedicException
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

                    "CONSULTA_CLINICA" -> {
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
        var count=0
        do { count++
            doctor = doctors.filter { it.specialty == Specialty.CONSULTA_CLINICA }.random()
        } while (!doctor.chatEnable&&count<20)
if(doctor.chatEnable==false){
    throw NoMedicException("NO SE AH ENCONTRADO UN MEDICO DISPONIBLE ")
}
     doctor.changeChatEnable(false)
        return doctor
    }


fun addDoctor(doctor: Doctor, context: Context){
    val  dbRegister = FirebaseFirestore.getInstance()
    val userEntry = hashMapOf(
        "chatavailable" to true,
        "dni" to doctor.dni,
        "isDoctor" to true,
        "name" to doctor.name,
        "surname" to doctor.surname,
        "email" to doctor.email,
        "especialidad" to doctor.specialty.toString()
    )
    dbRegister.collection("Doctor").document(doctor.email).set(userEntry).addOnCanceledListener {
        Toast.makeText(context,"NO SE PUDO AGREGAR LOS DATOS A LA BASE DE DATOS", Toast.LENGTH_LONG).show()
    }
}






}