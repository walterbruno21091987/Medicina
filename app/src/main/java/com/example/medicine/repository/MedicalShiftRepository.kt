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

    fun getMedicalShiftsAvailable() : List<MedicalShift> {
        return medicalShifts.filter { it.available==true} }



    fun add(newShifts:MedicalShift): Boolean {
        return medicalShifts.add(newShifts)
    }

   fun getMedicalShifts():List<MedicalShift>{
       return medicalShifts
   }
    fun getForNumberTurn(numTurno: Int): List<MedicalShift> {
     return medicalShifts.filter{ it.getnumTurno() == numTurno }
    }
}