package com.example.medicine.entities

import com.example.medicine.R
import com.example.medicine.repository.TurnoRepository

class Usuario(nombre:String, apellido:String, dni:Int,val contrasenia:String,val numberAfiliado:Int,val imageUser:Int =R.drawable.iconopersona):Persona(nombre,apellido,dni) {

    fun reservarTurno(numTurno:Int){
        val turno=TurnoRepository.getForNumberTurn(numTurno).first()
        turno.carnetAfiliado=numberAfiliado
        turno.disponible=false

    }
    companion object{
       fun  validatePassword(password:String):Boolean{
           var mayus=false
           var number=false
           var long=false
           var correct=false
           if(password.length>8){
               long=true
           }
           for(char in password){
               if(char>='A'&&char<='Z')mayus=true
               if(char>='0'&&char<='9')number=true}
           if(mayus&&number&&long) correct=true
           return correct
       }
    }
}