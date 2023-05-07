package com.example.medicine.repository

import com.example.medicine.entities.Turno
import java.time.LocalDate
import java.time.Month

object TurnoRepository {
    private var turnos : MutableList<Turno> = mutableListOf()
    init {

    }
    fun getForUser(dni: Int) : List<Turno> {
        val turnosDisponibles= turnos.filter { it.disponible==false }
        return  turnosDisponibles.filter { it.carnetAfiliado!!.equals(UsuarioReposirory.get(dni).numberAfiliado) }
    }

    fun getMedicalShiftsAvailable() : List<Turno> {
        return turnos.filter { it.disponible==true} }



    fun add(newShifts:Turno): Boolean {
        return turnos.add(newShifts)
    }

    fun getForNumberTurn(numTurno: Int): List<Turno> {
     return turnos.filter{ it.getnumTurno() == numTurno }
    }
}