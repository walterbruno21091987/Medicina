package com.example.medicine.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicine.R
import com.example.medicine.adapter.MedicalShiftAdapter
import com.example.medicine.databinding.FragmentSacarTurnoBinding
import com.example.medicine.entities.MedicalShift
import com.example.medicine.exception.EntryEmptyException
import com.example.medicine.repository.DoctorRepository
import com.example.medicine.repository.MedicalShiftRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SacarTurnoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SacarTurnoFragment : Fragment() {
    lateinit var contexto: Context
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentSacarTurnoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sacar_turno, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }



    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val email = arguments?.getString("EMAIL_USER") ?: ""
        val especialidadList: Array<String> = resources.getStringArray(R.array.especialidad)
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                view.context,
                android.R.layout.simple_dropdown_item_1line,
                especialidadList
            )
        binding.acEspecialty.setAdapter(adapter)
        listener(especialidadList, email)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun listener(especialidadList: Array<String>, email: String) {
        binding.btBuscar.setOnClickListener {

            val specialty = binding.acEspecialty.text.toString()
            try {
                if (!specialty.isEmpty() && especialidadList.contains(specialty)) {

                    val dbUser = FirebaseFirestore.getInstance()
                    binding.recyclerMedicalShift.layoutManager = LinearLayoutManager(contexto)
                    dbUser.collection("affiliate").document(email).get().addOnSuccessListener {

                        val affiliateCard: Int = it.get("affiliatenumber").toString().toInt()
                        binding.recyclerMedicalShift.adapter = MedicalShiftAdapter(
                            MedicalShiftRepository.getMedicalShiftsAvailable()
                                .filter { it.doctor.specialty.name == specialty },
                            affiliateCard,
                            contexto
                        )

                    }

                } else {
                    throw EntryEmptyException("DEBE INGRESAR UNA ESPECIALIDAD VALIDA")

                }
            } catch (e: EntryEmptyException) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                FirebaseCrashlytics.getInstance().recordException(e)
            }

        }
        binding.btSalirSacarTurnoFragment.setOnClickListener {
            findNavController().navigate(R.id.action_sacarTurnoFragment_to_userMenu)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SacarTurnoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SacarTurnoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}