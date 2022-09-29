package com.example.mobileassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivityForgetPasswordBinding
import com.google.firebase.database.FirebaseDatabase


class forgetPassword : AppCompatActivity() {
    private  lateinit var binding : ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        val binding: ActivityForgetPasswordBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_forget_password)


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")


    }
}