package com.example.medicine.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.medicine.R
import com.example.medicine.databinding.FragmentLogInBinding
import com.example.medicine.exception.EntryEmptyException
import com.example.medicine.exception.LoginFailedException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LogIn : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentLogInBinding
    lateinit var firebaseAnalytics:FirebaseAnalytics
    lateinit var firebaseAuth:FirebaseAuth
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
        firebaseAnalytics=Firebase.analytics

        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_log_in,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            val screenName="login"
            param(FirebaseAnalytics.Param.SCREEN_NAME,screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS,"loginFragment")
        }

        listener()
    }

    private fun listener() {
        binding.btIngresar.setOnClickListener {
            try {
                login()
            } catch (e: EntryEmptyException) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }
        binding.tvOlvidoContrasenia.setOnClickListener {
            binding.tvOlvidoContrasenia.setTextColor(Color.MAGENTA)
            findNavController().navigate(R.id.action_logIn_to_recoverPasswordFragment)
        }
        binding.tvRegistrarse.setOnClickListener {
            binding.tvRegistrarse.setTextColor(Color.MAGENTA)
            findNavController().navigate(R.id.action_logIn_to_registrarUsuarioFragment)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun login(){

        firebaseAuth=FirebaseAuth.getInstance()
        if(binding.etUser.text!!.isEmpty()){
         throw EntryEmptyException("Debe Ingresar Un Email")
     }

        val email=binding.etUser.text.toString()
        val password=binding.etPasword.text.toString()
        if(email=="ADMIN"&&password=="12345678"){
            findNavController().navigate(R.id.action_logIn_to_adminMenuFragment)
        }
    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener (requireActivity()){
 try{if(it.isSuccessful){
            navigateMenuUser()}
 else{
     throw LoginFailedException("USUARIO O CONTRASEÑA INCORRECTO")

 }}catch (e:LoginFailedException){
     Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
     FirebaseCrashlytics.getInstance().recordException(e)
 }
    }
    }

    private fun navigateMenuUser() {
        val email=binding.etUser.text.toString()
        val bundle= bundleOf("EMAIL" to email)
        findNavController().navigate(R.id.action_logIn_to_userMenu,bundle)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LogIn().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}