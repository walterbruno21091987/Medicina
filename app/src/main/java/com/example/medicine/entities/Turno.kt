package com.example.medicine.entities

import com.example.medicine.repository.TurnoRepository
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class Turno (var disponible:Boolean=true, val fecha: LocalDate, val hora:LocalTime, val dniMedico:Int, var carnetAfiliado:Int?=null,private var numTurno:Int){
    init {
        do{
        numTurno=Math.random().plus(100000000).toInt()}while(TurnoRepository.getForNumberTurn(numTurno)!=null)
    }
    fun getnumTurno():Int{
        return numTurno
    }
}