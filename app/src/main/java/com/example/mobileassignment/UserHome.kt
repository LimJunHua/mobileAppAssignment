package com.example.mobileassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.FragmentUserHomeBinding


import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class UserHome : Fragment() {
    private lateinit var binding: FragmentUserHomeBinding
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeBinding.inflate(layoutInflater)
        val firebase =FirebaseDatabase.getInstance()

        binding.btnBook.setOnClickListener(){
            val fragment = UserBookingFragment()
        }



        return inflater.inflate(R.layout.fragment_user_home, container, false)
    }


}