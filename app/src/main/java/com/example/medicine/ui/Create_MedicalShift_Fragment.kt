package com.example.medicine.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.medicine.R
import com.example.medicine.databinding.FragmentCreateMedicalShiftBinding
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.MedicalShift
import com.example.medicine.entities.Specialty
import com.example.medicine.repository.MedicalShiftRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Create_MedicalShift_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Create_MedicalShift_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentCreateMedicalShiftBinding

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
      binding=DataBindingUtil.inflate(inflater,R.layout.fragment_create__medical_shift_,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db=FirebaseFirestore.getInstance()

        binding.btAgregarTurno.setOnClickListener {
            val year =binding.etAnio.text.toString().toInt()
            val mhont =binding.etMes.text.toString().toInt()
            val day=binding.etDia.text.toString().toInt()
            val completionDate = LocalDate.of(year, mhont, day)
            val hour=binding.etHoras.text.toString().toInt()
            val minutes=binding.etMinutos.text.toString().toInt()
            val completionTime=LocalTime.of(hour,minutes)
            val email=binding.etMailDoctor.text.toString()
         db.collection("Doctor").document(email).get().addOnSuccessListener {
             var especialidad = Specialty.CONSULTA_CLINICA
             especialidad = specialty(it, especialidad)
             val doctor = Doctor(
                 it.get("name").toString(),
                 it.get("surname").toString(),
                 it.get("dni").toString().toInt(),
                 it.get("email").toString(),
                especialidad,
                it.get("chatavailable").toString().toBoolean())
             val newShift=MedicalShift(completionDate,completionTime,doctor)
             MedicalShiftRepository.add(newShift,context)

         }
        }

        binding.btSalirDeAgregarTurno.setOnClickListener {
            findNavController().navigate(R.id.action_create_MedicalShift_Fragment_to_adminMenuFragment)
        }
    }

    private fun specialty(
        it: DocumentSnapshot,
        especialidad: Specialty
    ): Specialty {
        var especialidad1 = especialidad
        when (it.get("especialidad").toString()) {
            "CARDIOLOGIA" -> {
                especialidad1 = Specialty.CARDIOLOGIA
            }

            "ODONTOLOGIA" -> {
                especialidad1 = Specialty.ODONTOLOGIA
            }

            "TRAUMATOLOGIA" -> {
                especialidad1 = Specialty.TRAUMATOLOGIA
            }

            "OFTALMOLOGIA" -> {
                especialidad1 = Specialty.OFTALMOLOGIA
            }

            "PSICOLOGIA" -> {
                especialidad1 = Specialty.PSICOLOGIA
            }

            "RADIOLOGIA" -> {
                especialidad1 = Specialty.RADIOLOGIA
            }

            "DERMATOLOGIA" -> {
                especialidad1 = Specialty.DERMATOLOGIA
            }

            "PEDIATRIA" -> {
                especialidad1 = Specialty.PEDIATRIA
            }

            "CONSULTA_CLINICA" -> {
                especialidad1 = Specialty.CONSULTA_CLINICA
            }
        }
        return especialidad1
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Create_MedicalShift_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Create_MedicalShift_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}