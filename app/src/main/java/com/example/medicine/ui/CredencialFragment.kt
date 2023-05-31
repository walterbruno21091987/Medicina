package com.example.medicine.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.medicine.R
import com.example.medicine.databinding.FragmentCredencialBinding
import com.example.medicine.entities.Affiliate
import com.example.medicine.repository.AffiliateRepository


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CredencialFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentCredencialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding=DataBindingUtil.inflate(inflater,R.layout.fragment_credencial,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email=arguments?.getString("EMAIL_USER")?:""

        val user=AffiliateRepository.get(email)
      if(email!=""){
        render(user)}
    }

    private fun render(affiliate: Affiliate) {
      binding.imUsario.setImageResource(affiliate.imageUser)
      binding.tvApellidoUsuario.text=affiliate.surname
        binding.tvNombreUsuario.text=affiliate.name
        binding.tvDniUsuario.text=affiliate.dni.toString()
        binding.tvNumeroAfiliadoUsuario.text=affiliate.affiliatenumber.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CredencialFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CredencialFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}