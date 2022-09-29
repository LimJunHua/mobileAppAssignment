package com.example.mobileassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivityUserMainBinding
import com.google.firebase.auth.FirebaseAuthException

class UserHome : Fragment() {
    private lateinit var binding: ActivityUserMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityUserMainBinding.inflate(layoutInflater)

        return inflater.inflate(R.layout.fragment_user_home, container, false)
    }


}