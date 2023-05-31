package com.example.medicine.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.medicine.entities.Specialty
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.MedicalShift
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
object MedicalShiftRepository {
    private var medicalShifts : MutableList<MedicalShift> = mutableListOf()
    init {
       val newDoctor= Doctor("Juan","Gonzalez",332455987,"aqui debe ir un mail",Specialty.CARDIOLOGIA)
        val newDoctor2= Doctor("Carlos","Ramirez",332455987,"aqui debe ir un mail",Specialty.CONSULTA_CLINICA)
  medicalShifts.add(MedicalShift(LocalDate.now(), LocalTime.now(),newDoctor))
        medicalShifts.add(MedicalShift(LocalDate.now(), LocalTime.now(),newDoctor))
        medicalShifts.add(MedicalShift(LocalDate.now(), LocalTime.now(),newDoctor2))
        medicalShifts.add(MedicalShift(LocalDate.now(), LocalTime.now(),newDoctor))
        medicalShifts.add(MedicalShift(LocalDate.now(), LocalTime.of(15,45),newDoctor2))
        medicalShifts.add(MedicalShift(LocalDate.now(), LocalTime.of(16,30),newDoctor2))


    }
    fun getForSpecialty(specialty: Specialty):List<MedicalShift>{
    return medicalShifts.filter { it.doctor.specialty.equals(specialty)}
    }

    fun getForUser(email: String) : List<MedicalShift> {
        val turnosDisponibles= medicalShifts.filter { it.available==false }
        return  turnosDisponibles.filter { it.affiliateCard!!.equals(AffiliateRepository.get(email).affiliatenumber) }
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