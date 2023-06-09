package com.example.medicine.ui

import android.annotation.SuppressLint
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
import com.example.medicine.R
import com.example.medicine.adapter.ChatAdapter
import com.example.medicine.databinding.FragmentChatBinding
import com.example.medicine.entities.Message
import com.example.medicine.repository.RepositoryChat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var firebaseAnalytics:FirebaseAnalytics
   lateinit var binding:FragmentChatBinding
   private var param1: String? = null
    private var param2: String? = null
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

        firebaseAnalytics= Firebase.analytics
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_chat, container, false)
        return binding.root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto=context
    }
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            val screenName="chat"
            param(FirebaseAnalytics.Param.SCREEN_NAME,screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS,"chat")
        }



        val email=arguments?.getString("EMAIL_USER")?:""
        val chatListView=binding.chatListView

        val db = FirebaseFirestore.getInstance()
        db.collection(email)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    // Maneja el error si ocurre algún problema al escuchar los cambios
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    RepositoryChat.refreshChat(email, chatListView,contexto)
                }
            }
        binding.sendButton.setOnClickListener {
        val content:String=binding.messageEditText.text.toString()
            RepositoryChat.sendMessage(email, chatListView, content,contexto)
            binding.messageEditText.setText(" ")
        }

binding.btFinalizarConsulta.setOnClickListener {
RepositoryChat.deleteChat(email)
    RepositoryChat.refreshChat(email,chatListView,contexto)
findNavController().navigate(R.id.action_chatFragment_to_logIn)
}

    }

    @RequiresApi(Build.VERSION_CODES.O)



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}