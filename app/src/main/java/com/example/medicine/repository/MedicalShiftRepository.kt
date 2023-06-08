package com.example.medicine.repository

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.medicine.entities.Specialty
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.MedicalShift
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalTime


@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
object MedicalShiftRepository {

    private var medicalShifts : MutableList<MedicalShift> = mutableListOf()
    init {
      /*  val doctor=Doctor("walter","bruno",33252293,"soyDoctor@hotmail.com", Specialty.CARDIOLOGIA)
        val doctor2=Doctor("adrian","Imbriano",33252293,"soyDoctor@hotmail.com", Specialty.CARDIOLOGIA)
        val doctor3=Doctor("meQuedeSinIdeas","bruno",33252293,"soyDoctor@hotmail.com", Specialty.CARDIOLOGIA)
   val medicalShift1=MedicalShift(LocalDate.now(), LocalTime.now(),doctor)
        medicalShifts.add(medicalShift1)
        val medicalShift2=MedicalShift(LocalDate.now(), LocalTime.now(),doctor2)
        medicalShifts.add(medicalShift2)
        val medicalShift3=MedicalShift(LocalDate.now(), LocalTime.now(),doctor3)
        medicalShifts.add(medicalShift3)
*//*

    val dbmedicaShift=FirebaseFirestore.getInstance()
         dbmedicaShift.collection("medicalShift").get().addOnSuccessListener {
           for(document in it) {

               val doctor=Doctor("walter","bruno",33252293,"soyDoctor@hotmail.com", Specialty.CARDIOLOGIA)
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
*/
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