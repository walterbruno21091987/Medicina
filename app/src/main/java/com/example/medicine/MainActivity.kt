package com.example.medicine

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.Specialty
import com.example.medicine.repository.DoctorRepository
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadDataDoctor()


        val navController=findNavController(R.id.nav_host_fragment_activity_main)
    }

    private fun loadDataDoctor() {
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
                    especialidad
                )
                DoctorRepository.addDoctor(doctor)
            }
        }
    }
}