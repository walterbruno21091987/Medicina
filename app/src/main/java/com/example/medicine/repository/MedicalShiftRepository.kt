package com.example.medicine.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.medicine.entities.Specialty
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.MedicalShift
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
object MedicalShiftRepository {

    private var medicalShifts : MutableList<MedicalShift> = mutableListOf()
    init {
    val dbmedicaShift=FirebaseFirestore.getInstance()
         dbmedicaShift.collection("medicalShift").get().addOnSuccessListener {
           for(document in it) {
               val email = document.get("email").toString()
               val doctor = DoctorRepository.getDoctorForEmail(email)
               val year=document.get("year").toString().toInt()
               val mont=document.get("mont").toString().toInt()
               val day=document.get("day").toString().toInt()
               val hour=document.get("hour").toString().toInt()
               val minute=document.get("minute").toString().toInt()
               val numMedicalShift=document.get("numMedicalShift").toString().toInt()
               val numAffiliateCard= document.get("affiliateCard").toString().toInt()
               val available=document.get("available").toString().toBoolean()
               val medicalShift=MedicalShift(LocalDate.of(year,mont,day), LocalTime.of(hour,minute),doctor,numMedicalShift,numAffiliateCard,available)
               medicalShifts.add(medicalShift)
           }      }

    }
    fun getForSpecialty(specialty: Specialty):List<MedicalShift>{
    return medicalShifts.filter { it.doctor.specialty.equals(specialty)}
    }

    fun getMedicalShiftsAvailable() : List<MedicalShift> {
        return medicalShifts.filter { it.available==true} }



    fun add(newShifts:MedicalShift): Boolean {
        return medicalShifts.add(newShifts)
    }

    fun getForNumberTurn(numTurno: Int): List<MedicalShift> {
     return medicalShifts.filter{ it.getnumTurno() == numTurno }
    }
}