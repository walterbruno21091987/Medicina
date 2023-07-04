package com.example.medicine.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.NavHostFragment
import com.example.medicine.entities.MedicalShift
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalTime


@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
object MedicalShiftRepository {
  @SuppressLint("StaticFieldLeak")
  private val db = FirebaseFirestore.getInstance()
    private var medicalShifts : MutableList<MedicalShift> = mutableListOf()
 fun loadDataMedicalShift() {
     medicalShifts.clear()

        db.collection("medicalShift").get().addOnSuccessListener {
            for (document in it) {
                val doctor = DoctorRepository.getDoctorForEmail(document.get("doctor").toString())
                val year = document.get("year").toString().toInt()
                val mont = document.get("mont").toString().toInt()
                val day = document.get("day").toString().toInt()
                val hour = document.get("hour").toString().toInt()
                val minute = document.get("minute").toString().toInt()
                val numMedicalShift = document.get("numMedicalShift").toString().toInt()
                val numAffiliateCard = document.get("affiliateCard").toString().toInt()
                val available = document.get("available").toString().toBoolean()
                val medicalShift = MedicalShift(LocalDate.of(year, mont, day), LocalTime.of(hour, minute), doctor, numMedicalShift, numAffiliateCard, available)
                medicalShifts.add(medicalShift)
            }
        }
    }

    fun getMedicalShiftsAvailable() : List<MedicalShift> {
        return medicalShifts.filter { it.available==true} }



    fun add(newShifts: MedicalShift, context: Context?) {
        db.collection("medicalShift").document(newShifts.numMedicalShift.toString()).set(
            hashMapOf("available" to true,"affiliateCard" to 0,"doctor" to newShifts.doctor.email,"day" to newShifts.fecha.dayOfMonth, "mont" to newShifts.fecha.monthValue, "year" to newShifts.fecha.year,"hour" to newShifts.hora.hour,"minute" to newShifts.hora.minute,"numMedicalShift" to newShifts.numMedicalShift)
        ).addOnCompleteListener {
            Toast.makeText(context,"TURNO AGREGADO CORRECTAMENTE",Toast.LENGTH_LONG).show()

        }.addOnFailureListener {
            Toast.makeText(context,"NO SE PUDO AGREGAR EL TURNO",Toast.LENGTH_LONG).show()
        }
    }

   fun getMedicalShifts():List<MedicalShift>{
       return medicalShifts
   }
    fun getForNumberTurn(numTurno: Int): List<MedicalShift> {
     return medicalShifts.filter{ it.getnumTurno() == numTurno }
    }
    fun setAvailable(medicalShift:MedicalShift,affiliateCard:Int){
        db.collection("medicalShift").document(medicalShift.numMedicalShift.toString()).set(
            hashMapOf("available" to false,"affiliateCard" to affiliateCard,"doctor" to medicalShift.doctor.email,"day" to medicalShift.fecha.dayOfMonth, "mont" to medicalShift.fecha.monthValue, "year" to medicalShift.fecha.year,"hour" to medicalShift.hora.hour,"minute" to medicalShift.hora.minute,"numMedicalShift" to medicalShift.numMedicalShift)
        )
    }
}