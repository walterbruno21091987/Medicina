package com.example.medicine.repository

import com.example.medicine.databinding.FragmentCredencialBinding
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





}