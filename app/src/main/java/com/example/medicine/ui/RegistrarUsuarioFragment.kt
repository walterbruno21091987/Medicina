package com.example.medicine.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.medicine.R
import com.example.medicine.databinding.FragmentRegistrarUsuarioBinding
import com.example.medicine.entities.Affiliate
import com.example.medicine.entities.User
import com.example.medicine.exception.EntryEmptyException
import com.example.medicine.exception.InsecurePasswordException

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class RegistrarUsuarioFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentRegistrarUsuarioBinding
    lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var dbRegister:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_registrar_usuario,container,false)
       firebaseAnalytics=Firebase.analytics
        firebaseAuth= FirebaseAuth.getInstance()
        dbRegister = FirebaseFirestore.getInstance()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            val screenName="registrar usuario"
            param(FirebaseAnalytics.Param.SCREEN_NAME,screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS,"RegistrarUsuarioFragment")
        }
        listener()

    }

    private fun listener() {

    //REGISTRARSE
        binding.btRegistrar.setOnClickListener {
            try {
                try {
                   registrerUser()
                } catch (e: InsecurePasswordException) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    FirebaseCrashlytics.getInstance().recordException(e) }
                } catch (ex: EntryEmptyException) {
                Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
                FirebaseCrashlytics.getInstance().recordException(ex)  }
        }





    }




    @SuppressLint("SuspiciousIndentation")
    private fun registrerUser() {

        fieldValidation()

        val email=binding.etUserRegister.text.toString()
      val password=binding.etPasswordRegister.text.toString()
        if(Affiliate.validatePassword(password)==false) throw InsecurePasswordException("la contraseña debe tener almenos 8 caracteres,una mayuscula y un numero")
      val nombre=binding.etNameRegister.text.toString()
      val apellido=binding.etSurnameRegister.text.toString()
      val numeroAfiliado=binding.numeroDeAfiliado.text.toString().toInt()
     val dni=binding.etDniUser.text.toString().toInt()
        val affiliate=Affiliate(nombre,apellido,dni,email,numeroAfiliado)
       addlogin(affiliate,password)
       createDataUser(affiliate)
        createDataAffiliate(affiliate)




    }




    private fun fieldValidation() {
        if (binding.etUserRegister.text!!.isEmpty() || binding.etNameRegister.text!!.isEmpty() || binding.etSurnameRegister.text!!.isEmpty() || binding.numeroDeAfiliado.text!!.isEmpty()) {
            throw EntryEmptyException("debe completar todos los campos")
        }
    }

    private fun createDataUser(user: User) {

        val userEntry = hashMapOf(
            "DNI" to user.dni,
            "isDoctor" to user.isDoctor,
            "name" to user.name,
            "surname" to user.surname,
            "email" to user.email
        )
        dbRegister.collection("users").document(user.email).set(userEntry).addOnCanceledListener {
            Toast.makeText(context,"NO SE PUDO AGREGAR LOS DATOS A LA BASE DE DATOS",Toast.LENGTH_LONG).show()
        }
    }
    private fun createDataAffiliate(affiliate: Affiliate) {

        val userEntry = hashMapOf(
            "DNI" to affiliate.dni,
            "isDoctor" to affiliate.isDoctor,
            "name" to affiliate.name,
            "surname" to affiliate.surname,
            "email" to affiliate.email,
            "affiliatenumber" to affiliate.affiliatenumber
        )
        dbRegister.collection("affiliate").document(affiliate.email).set(userEntry).addOnCanceledListener {
            Toast.makeText(context,"NO SE PUDO AGREGAR LOS DATOS A LA BASE DE DATOS",Toast.LENGTH_LONG).show()
        }
    }
    private fun addlogin(user: User, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password).addOnSuccessListener {
            Toast.makeText(context,"USUARIO AGREGADO",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_registrarUsuarioFragment_to_logIn)
        }
    }






    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrarUsuarioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}