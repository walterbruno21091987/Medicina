package com.example.medicine.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicine.R
import com.example.medicine.adapter.MedicalShiftAdapter
import com.example.medicine.databinding.FragmentSacarTurnoBinding
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.MedicalShift
import com.example.medicine.entities.Specialty
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
 lateinit var  binding:FragmentSacarTurnoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_sacar_turno, container, false)
        loadDataMedicalShift()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto=context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val email=arguments?.getString("EMAIL_USER")?:""

        binding.btBuscar.setOnClickListener {
        try{   if(!binding.acEspecialty.text.isEmpty()){
            val especialidad=binding.acEspecialty.text.toString()

          binding.recyclerMedicalShift.layoutManager=LinearLayoutManager(contexto)

            binding.recyclerMedicalShift.adapter=MedicalShiftAdapter(MedicalShiftRepository.getMedicalShiftsAvailable().filter { it.doctor.specialty.name == especialidad },12345678,contexto)

            }else{
                throw EntryEmptyException("DEBE INGRESAR UNA ESPECIALIDAD")
           }}catch (e:EntryEmptyException){
               Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
            FirebaseCrashlytics.getInstance().recordException(e)
           }

        }
        }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadDataMedicalShift() {
        val db = FirebaseFirestore.getInstance()
        db.collection("medicalShift").get().addOnSuccessListener {
            for (document in it) {
                val doctor = DoctorRepository.getDoctorForEmail(document.get("doctor").toString())
                val year = document.get("year").toString().toInt()
                val mont = document.get("mont").toString().toInt()
                val day = document.get("day").toString().toInt()
                val hour = document.get("hour").toString().toInt()
                val minute = document.get("minute").toString().toInt()
                val numMedicalShift = document.get("numMedicalShift").toString().toInt()
                val numAffiliateCard = document.get("affiliateCard").toString().toInt()
                val available = document.get("available").toString().toBoolean()
                val medicalShift = MedicalShift(LocalDate.of(year, mont, day), LocalTime.of(hour, minute), doctor, numMedicalShift, numAffiliateCard, available)
                MedicalShiftRepository.add(medicalShift)
            }
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