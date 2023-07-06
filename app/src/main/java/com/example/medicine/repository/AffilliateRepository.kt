package com.example.medicine.repository

import android.content.Context
import android.widget.Toast
import com.example.medicine.databinding.FragmentCredencialBinding
import com.example.medicine.entities.Affiliate
import com.google.firebase.firestore.FirebaseFirestore

object AffilliateRepository {
    fun renderAffiliateCard(email: String, binding: FragmentCredencialBinding) {
        val db= FirebaseFirestore.getInstance()
        db.collection("affiliate").document(email).get().addOnSuccessListener {
            binding.tvApellidoUsuario.text=it.get("surname").toString()
            binding.tvNombreUsuario.text=it.get("name").toString()
            binding.tvDniUsuario.text=it.get("DNI").toString()
            binding.tvNumeroAfiliadoUsuario.text=it.get("affiliatenumber").toString()
        }
    }
     fun createDataAffiliate(affiliate: Affiliate,context:Context) {
       val  dbRegister = FirebaseFirestore.getInstance()
        val userEntry = hashMapOf(
            "DNI" to affiliate.dni,
            "isDoctor" to affiliate.isDoctor,
            "name" to affiliate.name,
            "surname" to affiliate.surname,
            "email" to affiliate.email,
            "affiliatenumber" to affiliate.affiliatenumber
        )
        dbRegister.collection("affiliate").document(affiliate.email).set(userEntry).addOnCanceledListener {
            Toast.makeText(context,"NO SE PUDO AGREGAR LOS DATOS A LA BASE DE DATOS", Toast.LENGTH_LONG).show()
        }
    }




}