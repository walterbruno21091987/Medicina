package com.example.medicine.repository
import com.example.medicine.entities.Affiliate
import com.google.firebase.auth.FirebaseAuth

lateinit var  firebaseAuth: FirebaseAuth
object AffiliateRepository {
    private var Affiliates : MutableList<Affiliate> = mutableListOf()

    init {
firebaseAuth=FirebaseAuth.getInstance()
    }

    //traer de la firebase por que rompe//
    fun get(email: String) : Affiliate {
        return Affiliates.first { it.email == email }

    }

    /*fun get() : MutableList<Usuario> {
        return users
    }*/

    fun add(newAffiliate:Affiliate): Boolean {

        //val dbRegister=FirebaseFirestore.getInstance()
        //dbRegister.collection(users).document(email).set(hashMapof())
        return Affiliates.add(newAffiliate)


    }
}
