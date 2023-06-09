package com.example.medicine.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicine.R
import com.example.medicine.adapter.MedicalShiftAdapter
import com.example.medicine.databinding.FragmentListMedicalShiftForUserBinding
import com.example.medicine.repository.MedicalShiftRepository
import com.google.firebase.firestore.FirebaseFirestore


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ListMedicalShiftForUserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
  lateinit var binding:FragmentListMedicalShiftForUserBinding
    lateinit var contexto: Context
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
       binding=DataBindingUtil.inflate(inflater,R.layout.fragment_list_medical_shift_for_user,container,false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto=context
    }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MedicalShiftRepository.loadDataMedicalShift()
        val dbUser=FirebaseFirestore.getInstance()
        val email=arguments?.getString("EMAIL_USER")?:""
    

        binding.recyclerMedicalShiftForUser.layoutManager=LinearLayoutManager(context)
        dbUser.collection("affiliate").document(email).get().addOnSuccessListener {

            val affiliateCard=it.get("affiliatenumber").toString().toInt()
            binding.recyclerMedicalShiftForUser.adapter=MedicalShiftAdapter(MedicalShiftRepository.getMedicalShifts().filter { it.affiliateCard==affiliateCard },affiliateCard,contexto)
        }

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListMedicalShiftForUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}