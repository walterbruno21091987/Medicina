package com.example.medicine.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.medicine.R
import com.example.medicine.databinding.FragmentRegistrarUsuarioBinding
import com.example.medicine.entities.Usuario
import com.example.medicine.exception.EntryEmptyException
import com.example.medicine.exception.InsecurePasswordException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [RegistrarUsuarioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener()
    }

    private fun listener() {
        binding.btRegistrar.setOnClickListener {
            try {
                try {
                    registrerUser()
                } catch (e: InsecurePasswordException) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            } catch (ex: EntryEmptyException) {
                Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun registrerUser() {
        fieldValidation()
        val userDni=binding.etUserRegister.text.toString().toInt()
      val password=binding.etPasswordRegister.text.toString()
        if(Usuario.validatePassword(password)==false) throw InsecurePasswordException("la contraseña debe tener almenos 8 caracteres,una mayuscula y un numero")
      val nombre=binding.etNameRegister.text.toString()
      val apellido=binding.etSurnameRegister.text.toString()
      val numeroAfiliado=binding.numeroDeAfiliado.text.toString().toInt()

    }
    private fun fieldValidation() {
        if (binding.etUserRegister.text!!.isEmpty() || binding.etNameRegister.text!!.isEmpty() || binding.etSurnameRegister.text!!.isEmpty() || binding.numeroDeAfiliado.text!!.isEmpty()) {
            throw EntryEmptyException("debe completar todos los campos")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistrarUsuarioFragment.
         */
        // TODO: Rename and change types and number of parameters
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