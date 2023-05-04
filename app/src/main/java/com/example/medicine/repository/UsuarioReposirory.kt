package com.example.medicine.repository
import com.example.medicine.entities.Usuario

object UsuarioReposirory {
    private var users : MutableList<Usuario> = mutableListOf()
    init {

    }
    fun get(dni: Int) : Usuario {

        return users.first { it.dni == dni }
    }

    /*fun get() : MutableList<Usuario> {
        return users
    }*/

    fun add(newUser:Usuario): Boolean {
        return users.add(newUser)
    }
}
