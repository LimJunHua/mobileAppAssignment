package com.example.mobileassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivityPendingVerificationBinding
import com.google.firebase.database.FirebaseDatabase
import java.lang.String


class pendingVerification : AppCompatActivity() {
    private  lateinit var binding : ActivityPendingVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_verification)
        val binding: ActivityPendingVerificationBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_pending_verification)
        val  bundle = intent.extras
        if (bundle != null) {
            val name = " ${bundle.getString("name")}"
            val username = " ${bundle.getString("username")}"
            val icNumber = " ${bundle.getString("iCNumber")}"
            val phoneNumber = " ${bundle.getString("phoneNumber")}"
            val email = " ${bundle.getString("email")}"
            val password = " ${bundle.getString("password")}"
            binding.tvSignUpMessage.text = String.format("the code has send to %s, please insert the code at below",email)

        }
            //}




        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")


    }
}