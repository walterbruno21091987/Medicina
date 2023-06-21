package com.example.medicine

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.medicine.entities.Doctor
import com.example.medicine.entities.Specialty
import com.example.medicine.repository.DoctorRepository
import com.example.medicine.repository.MedicalShiftRepository
import com.example.medicine.repository.RepositoryChat
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DoctorRepository.loadDataDoctor()


        val navController=findNavController(R.id.nav_host_fragment_activity_main)
    }


}