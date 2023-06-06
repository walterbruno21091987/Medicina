package com.example.medicine.entities

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.medicine.R
import com.example.medicine.repository.MedicalShiftRepository
import com.google.firebase.firestore.FirebaseFirestore

class Affiliate(name:String, surname:String, dni:Int, email:String, val affiliatenumber:Int, val imageUser:Int =R.drawable.iconopersona, isDoctor:Boolean=false):User(name,surname,dni,email,isDoctor) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bookMedicalShift(numMedicalShift:Int){
        val medicalShift=MedicalShiftRepository.getForNumberTurn(numMedicalShift).first()
        medicalShift.affiliateCard=affiliatenumber//ojo!!! esto se debe modificar en la base de datos
        medicalShift.available=false//ojo!!! esto se debe modificar en la base de datos
        val dbChangeData=FirebaseFirestore.getInstance()
        dbChangeData.collection("medicalShift").document(numMedicalShift.toString()).set(
            hashMapOf("available" to false,"affiliateCard" to affiliatenumber)
        )

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