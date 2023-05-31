package com.example.medicine.entities

class Doctor (name:String, surname:String, dni:Int, email:String,val specialty: Specialty, isDoctor:Boolean=true) :User(name,surname,dni,email,isDoctor) {
}