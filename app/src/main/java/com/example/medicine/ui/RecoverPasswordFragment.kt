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
import com.example.medicine.databinding.FragmentRecoverPasswordBinding
import com.example.medicine.exception.EntryEmptyException
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RecoverPasswordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
private lateinit var binding:FragmentRecoverPasswordBinding
    private  lateinit var  firebaseAuth:FirebaseAuth
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

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_recover_password,container,false)
        firebaseAuth=FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener()

    }

    private fun listener() {
        binding.btEnviarMailRecoverPassword.setOnClickListener {
            val email = binding.etRecoverPassword.text.toString()
            try {
                if (email.isEmpty()) {
                    throw EntryEmptyException("DEBE INGRESAR UN MAIL")
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {

                        Toast.makeText(context, " REVISE SU CASILLA DE CORREO", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            } catch (e: EntryEmptyException) {
                Toast.makeText(context, "DEBE INGRESAR UN MAIL", Toast.LENGTH_LONG).show()
            }

        }
        binding.btSalir.setOnClickListener {
            findNavController().navigate(R.id.action_recoverPasswordFragment_to_logIn)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecoverPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}