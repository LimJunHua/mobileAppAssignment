package com.example.mobileassignment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class verificationRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_register)
        val sharedPref = getSharedPreferences("addName", Context.MODE_PRIVATE)

        val userEmail = sharedPref.getString("email","default value")
        val username =  sharedPref.getString("name","default value")
        val userIC = sharedPref.getString("iCNumber","default value")
        val userPhone = sharedPref.getString("phoneNumber","default value")
        val userPassword =  sharedPref.getString("password","default value")
        val verificationCode = sharedPref.getInt("verificationCode",0)


    }
}