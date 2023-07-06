package com.example.medicine.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.medicine.R
import com.example.medicine.databinding.FragmentCreateDoctorBinding
import com.example.medicine.entities.Affiliate
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.Specialty
import com.example.medicine.entities.User
import com.example.medicine.exception.EntryEmptyException
import com.example.medicine.exception.InsecurePasswordException
import com.example.medicine.repository.DoctorRepository
import com.example.medicine.repository.UserRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateDoctorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateDoctorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var binding:FragmentCreateDoctorBinding
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
        firebaseAuth= FirebaseAuth.getInstance()
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_create_doctor,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            val screenName="create doctor"
            param(FirebaseAnalytics.Param.SCREEN_NAME,screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS,"CreateDoctorFragment")
        }
        val especialidadList: Array<String> = resources.getStringArray(R.array.especialidad)
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                view.context,
                android.R.layout.simple_dropdown_item_1line,
                especialidadList
            )
        binding.acEspecialtyDoctor.setAdapter(adapter)

        listener(especialidadList)
    }

    private fun listener(especialidadList: Array<String>) {
binding.btRegistrarMedico.setOnClickListener {
    try {
        try {
            registrerDoctor(especialidadList)
        } catch (e: InsecurePasswordException) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            FirebaseCrashlytics.getInstance().recordException(e) }
    } catch (ex: EntryEmptyException) {
        Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
        FirebaseCrashlytics.getInstance().recordException(ex)  }
}
    }

    private fun registrerDoctor(especialidadList: Array<String>) {

        val especialidad=binding.acEspecialtyDoctor.text.toString()
        fieldValidation(especialidadList,especialidad)

        var tipoEspecialidad = Specialty.CONSULTA_CLINICA
        when (especialidad) {
            "CARDIOLOGIA" -> {
                tipoEspecialidad = Specialty.CARDIOLOGIA
            }

            "ODONTOLOGIA" -> {
                tipoEspecialidad = Specialty.ODONTOLOGIA
            }

            "TRAUMATOLOGIA" -> {
                tipoEspecialidad = Specialty.TRAUMATOLOGIA
            }

            "OFTALMOLOGIA" -> {
                tipoEspecialidad = Specialty.OFTALMOLOGIA
            }

            "PSICOLOGIA" -> {
                tipoEspecialidad = Specialty.PSICOLOGIA
            }

            "RADIOLOGIA" -> {
                tipoEspecialidad = Specialty.RADIOLOGIA
            }

            "DERMATOLOGIA" -> {
                tipoEspecialidad = Specialty.DERMATOLOGIA
            }

            "PEDIATRIA" -> {
                tipoEspecialidad = Specialty.PEDIATRIA
            }

            "CONSULTA_CLINICA" -> {
                tipoEspecialidad = Specialty.CONSULTA_CLINICA
            }
        }
        val nameDoctor=binding.etNameRegisterDoctor.text.toString()
        val surnameDoctor=binding.etSurnameRegisterDoctor.text.toString()
        val email=binding.etUserRegisterDoctor.text.toString()
        val dni=binding.etDniDoctor.text.toString().toInt()
        val doctor= Doctor(nameDoctor,surnameDoctor,dni,email,tipoEspecialidad)
        val contrasenia=binding.etPasswordRegisterDoctor.text.toString()
        if(Affiliate.validatePassword(contrasenia)==false) throw InsecurePasswordException("la contraseña debe tener almenos 8 caracteres,una mayuscula y un numero")
        addlogin(doctor,contrasenia)
        UserRepository.createDataUser(doctor,requireContext())
        DoctorRepository.addDoctor(doctor,requireContext())

    }


    private fun addlogin(user: User, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password).addOnSuccessListener {
            Toast.makeText(context,"USUARIO AGREGADO", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_createDoctorFragment_to_adminMenuFragment)
        }
    }
    private fun fieldValidation(especialidadList: Array<String>, especialidad: String) {
        if (binding.etUserRegisterDoctor.text!!.isEmpty() || binding.etNameRegisterDoctor.text!!.isEmpty() || binding.etSurnameRegisterDoctor.text!!.isEmpty() || binding.acEspecialtyDoctor.text!!.isEmpty()||!especialidadList.contains(especialidad)) {
            throw EntryEmptyException("debe completar todos los campos y una especialidad del listado")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateDoctorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateDoctorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}