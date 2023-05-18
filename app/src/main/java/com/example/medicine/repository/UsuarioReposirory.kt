package com.example.medicine.repository
import com.example.medicine.entities.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
lateinit var  firebaseAuth: FirebaseAuth
object UsuarioReposirory {
    private var users : MutableList<Usuario> = mutableListOf()

    init {
firebaseAuth=FirebaseAuth.getInstance()
    }

    //traer de la firebase por que rompe//
    fun get(email: String) : Usuario {
        return users.first { it.email == email }
    }

    /*fun get() : MutableList<Usuario> {
        return users
    }*/

    fun add(newUser:Usuario): Boolean {
        firebaseAuth.createUserWithEmailAndPassword(newUser.email,newUser.contrasenia)
        //val dbRegister=FirebaseFirestore.getInstance()
        //dbRegister.collection(users).document(email).set(hashMapof())
        return users.add(newUser)


    }
}
