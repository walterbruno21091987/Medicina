package com.example.medicine.entities

class Usuario(nombre:String,apellido:String, dni:Int,val imagenUser:String?=null,val numeroAfiliado:Int):Persona(nombre,apellido,dni) {

}