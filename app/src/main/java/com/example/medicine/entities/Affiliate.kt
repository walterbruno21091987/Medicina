package com.example.medicine.entities

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.medicine.R
import com.example.medicine.repository.MedicalShiftRepository
import com.google.firebase.firestore.FirebaseFirestore

class Affiliate(name:String, surname:String, dni:Int, email:String, val affiliatenumber:Int, val imageUser:Int =R.drawable.iconopersona, isDoctor:Boolean=false):User(name,surname,dni,email,isDoctor) {




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