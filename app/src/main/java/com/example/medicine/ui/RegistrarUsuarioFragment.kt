package com.example.medicine.ui

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
import com.example.medicine.entities.Usuario
import com.example.medicine.exception.EntryEmptyException
import com.example.medicine.exception.InsecurePasswordException
import com.example.medicine.repository.UsuarioReposirory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class RegistrarUsuarioFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentRegistrarUsuarioBinding
    lateinit var firebaseAnalytics: FirebaseAnalytics

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
                   if(registrerUser())  {
                       Toast.makeText(context,"USUARIO AGREGADO",Toast.LENGTH_LONG).show()
                       findNavController().navigate(R.id.action_registrarUsuarioFragment_to_logIn)}
                } catch (e: InsecurePasswordException) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    FirebaseCrashlytics.getInstance().recordException(e) }
                } catch (ex: EntryEmptyException) {
                Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
                FirebaseCrashlytics.getInstance().recordException(ex)  }
        }





    }




    private fun registrerUser():Boolean {
        fieldValidation()
        val email=binding.etUserRegister.text.toString()
      val password=binding.etPasswordRegister.text.toString()
        if(Usuario.validatePassword(password)==false) throw InsecurePasswordException("la contraseña debe tener almenos 8 caracteres,una mayuscula y un numero")
      val nombre=binding.etNameRegister.text.toString()
      val apellido=binding.etSurnameRegister.text.toString()
      val numeroAfiliado=binding.numeroDeAfiliado.text.toString().toInt()
     val dni=binding.etDniUser.text.toString().toInt()
        val user=Usuario(nombre,apellido,dni,email,password,numeroAfiliado)
        return (UsuarioReposirory.add(user))
    }




    private fun fieldValidation() {
        if (binding.etUserRegister.text!!.isEmpty() || binding.etNameRegister.text!!.isEmpty() || binding.etSurnameRegister.text!!.isEmpty() || binding.numeroDeAfiliado.text!!.isEmpty()) {
            throw EntryEmptyException("debe completar todos los campos")
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