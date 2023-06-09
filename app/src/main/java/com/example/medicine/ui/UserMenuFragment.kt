package com.example.medicine.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.medicine.R
import com.example.medicine.databinding.FragmentUserMenuBinding
import com.example.medicine.repository.MedicalShiftRepository
import com.example.medicine.repository.RepositoryChat
import com.example.medicine.repository.UserRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserMenu.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserMenu : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentUserMenuBinding
    lateinit var firebaseAnalytics: FirebaseAnalytics
    @RequiresApi(Build.VERSION_CODES.O)
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
        firebaseAnalytics= Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            val screenName="userMenuFragment"
            param(FirebaseAnalytics.Param.SCREEN_NAME,screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS,"UserMenuFragment")
        }
       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_user_menu,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MedicalShiftRepository.loadDataMedicalShift()
        val bundle= arguments?.getString("EMAIL")
        val email= bundleOf("EMAIL_USER" to bundle)

        RepositoryChat.loadChat(email.toString())

        listener(email)

    }


    private fun listener(email: Bundle) {
        binding.btSalir.setOnClickListener {

            findNavController().navigate(R.id.action_userMenu_to_logIn)
        }
        binding.btCredencial.setOnClickListener {


            findNavController().navigate(R.id.action_userMenu_to_credencialFragment,email)
        }
        binding.btSacarTurno.setOnClickListener {
            findNavController().navigate(R.id.action_userMenu_to_sacarTurnoFragment,email)
        }
        binding.btHistorialTurno.setOnClickListener {

            findNavController().navigate(R.id.action_userMenu_to_listMedicalShiftForUserFragment,email)
        }
        binding.btConsultaOnline.setOnClickListener {

            findNavController().navigate(R.id.action_userMenu_to_chatFragment,email)
        }

    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserMenu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}