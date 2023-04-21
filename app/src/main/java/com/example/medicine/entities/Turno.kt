package com.example.medicine.entities

import java.util.*

class Turno (var disponible:Boolean=true,val fecha:Date,val especialidad:Especialidad,val nombreMedico:String,var carnetAfiliado:Int?=null){
}