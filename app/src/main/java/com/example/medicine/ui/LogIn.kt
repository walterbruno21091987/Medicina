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
import com.example.medicine.repository.UsuarioReposirory
import com.google.firebase.analytics.FirebaseAnalytics

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogIn.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogIn : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentLogInBinding
    lateinit var firebaseAnalytics:FirebaseAnalytics
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
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_log_in,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.btIngresar.setOnClickListener {
           try{     if(login()) navigateMenuUser()
           }catch (e:EntryEmptyException){
               Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
           }
       }
        binding.tvOlvidoContrasenia.setOnClickListener {
            binding.tvOlvidoContrasenia.setTextColor(Color.MAGENTA)
              }
        binding.tvRegistrarse.setOnClickListener {
            binding.tvRegistrarse.setTextColor(Color.MAGENTA)
        }
    }

    private fun login(): Boolean{
     if(binding.etUser.text!!.isEmpty()){
          throw EntryEmptyException("Debe Ingresar Un Usuario")
      }
     var loginCorrecto=true
        return loginCorrecto
    }

    private fun navigateMenuUser() {
        val dni=binding.etUser.text.toString().toInt()
        val bundle= bundleOf("DNI" to dni)
        findNavController().navigate(R.id.action_logIn_to_userMenu,bundle)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LogIn.
         */
        // TODO: Rename and change types and number of parameters
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