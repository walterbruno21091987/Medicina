package com.example.medicine.repository
import com.example.medicine.entities.Usuario
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object UsuarioReposirory {
    private var users : MutableList<Usuario> = mutableListOf()
    init {

    }
    fun get(email: String) : Usuario {
        return users.first { it.email == email }
    }

    /*fun get() : MutableList<Usuario> {
        return users
    }*/

    fun add(newUser:Usuario): Boolean {
        return users.add(newUser)
    }
}
