package com.example.medicine.entities

class Doctor (name:String, surname:String, dni:Int, email:String, val specialty: Specialty, val chatEnable: Boolean =true, isDoctor:Boolean=true) :User(name,surname,dni,email,isDoctor) {
}