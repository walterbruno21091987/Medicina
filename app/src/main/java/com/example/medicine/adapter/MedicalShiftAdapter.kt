package com.example.medicine.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.medicine.R
import com.example.medicine.databinding.FragmentRecoverPasswordBinding
import com.example.medicine.databinding.ItemTurnoBinding
import com.example.medicine.entities.MedicalShift
import com.google.firebase.firestore.FirebaseFirestore

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MedicalShiftViewHolder, position: Int) {
       db= FirebaseFirestore.getInstance()
        val medicalShift=medicalShiftList[position]
        binding.tvDoctor.text="DR. ${medicalShift.doctor.name} ${medicalShift.doctor.surname}"
        binding.tvFecha.text=medicalShift.fecha.toString()
        binding.tvHora.text=medicalShift.hora.toString()
        binding.tvEspecialidad.text=medicalShift.doctor.specialty.name
        binding.btRegistarTurno.setOnClickListener {
     db.collection("medicalShift").document(medicalShift.numMedicalShift.toString()).set(
         hashMapOf("available" to false,"affiliateCard" to affiliateCard,"doctor" to medicalShift.doctor.email,"day" to medicalShift.fecha.dayOfMonth, "mont" to medicalShift.fecha.monthValue, "year" to medicalShift.fecha.year,"hour" to medicalShift.hora.hour,"minute" to medicalShift.hora.minute,"numMedicaShift" to medicalShift.numMedicalShift)
     )


           Toast.makeText(context,"TURNO RESERVADO",Toast.LENGTH_LONG).show()
        }
    }
}