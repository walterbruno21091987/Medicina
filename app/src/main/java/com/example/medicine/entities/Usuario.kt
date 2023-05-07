package com.example.medicine.entities

import com.example.medicine.repository.TurnoRepository

class Usuario(nombre:String,apellido:String, dni:Int,val imageUser:String?="@drawable/iconopersona",val numberAfiliado:Int):Persona(nombre,apellido,dni) {

    fun reservarTurno(numTurno:Int){
        val turno=TurnoRepository.getForNumberTurn(numTurno).first()
        turno.carnetAfiliado=numberAfiliado
        turno.disponible=false
    }
}