package com.example.medicine.entities

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.medicine.repository.MedicalShiftRepository
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class MedicalShift (val fecha: LocalDate, val hora:LocalTime, val doctor:Doctor, var numMedicalShift:Int=Math.random().plus(100000000).toInt(), var affiliateCard:Int?=null, var available:Boolean=true){
    init {
        do{
        numMedicalShift=Math.random().plus(100000000).toInt()}while(!MedicalShiftRepository.getForNumberTurn(numMedicalShift).isEmpty())
    }
    fun getnumTurno():Int{
        return numMedicalShift
    }
}