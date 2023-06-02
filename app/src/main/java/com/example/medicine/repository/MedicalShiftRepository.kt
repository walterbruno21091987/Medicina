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
    lateinit var dbmedicaShift:FirebaseFirestore
    private var medicalShifts : MutableList<MedicalShift> = mutableListOf()
    init { //TRAER LOS TURNOS DE UNA BASE DE DATOS
    dbmedicaShift=FirebaseFirestore.getInstance()

        //aca se deberia traerla coleccion completa de turnos y guardarla en la lista de turnos "medicalShift"
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

    fun getMedicalShiftsAvailable() : List<MedicalShift> {
        return medicalShifts.filter { it.available==true} }



    fun add(newShifts:MedicalShift): Boolean {
        return medicalShifts.add(newShifts)
    }

    fun getForNumberTurn(numTurno: Int): List<MedicalShift> {
     return medicalShifts.filter{ it.getnumTurno() == numTurno }
    }
}