package com.example.medicine.entities

import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class Turno (var disponible:Boolean=true, val fecha: LocalDate, val hora:LocalTime, val dniMedico:Int, var carnetAfiliado:Int?=null){
}