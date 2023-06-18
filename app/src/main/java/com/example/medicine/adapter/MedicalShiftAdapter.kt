package com.example.medicine.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.medicine.R
import com.example.medicine.databinding.FragmentRecoverPasswordBinding
import com.example.medicine.databinding.ItemTurnoBinding
import com.example.medicine.entities.MedicalShift
import com.example.medicine.repository.MedicalShiftRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.NonDisposableHandle.parent

class MedicalShiftAdapter(val medicalShiftList:List<MedicalShift>,val affiliateCard:Int,val context:Context): RecyclerView.Adapter<MedicalShiftViewHolder>() {
    lateinit var binding: ItemTurnoBinding
    lateinit var db:FirebaseFirestore
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalShiftViewHolder {
      binding=DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_turno,parent,false)
    return MedicalShiftViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
       return medicalShiftList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MedicalShiftViewHolder, position: Int) {

        val medicalShift=medicalShiftList[position]
        if(!medicalShift.available){
            binding.btRegistarTurno.visibility=View.GONE
        }
        binding.tvDoctor.text="DR. ${medicalShift.doctor.name} ${medicalShift.doctor.surname}"
        binding.tvFecha.text=medicalShift.fecha.toString()
        binding.tvHora.text=medicalShift.hora.toString()
        binding.tvEspecialidad.text=medicalShift.doctor.specialty.name
        binding.btRegistarTurno.setOnClickListener {
       MedicalShiftRepository.setAvailable(medicalShift,affiliateCard)
            Toast.makeText(context,"TURNO RESERVADO",Toast.LENGTH_LONG).show()
            binding.btRegistarTurno.visibility=View.GONE
        }
    }
}