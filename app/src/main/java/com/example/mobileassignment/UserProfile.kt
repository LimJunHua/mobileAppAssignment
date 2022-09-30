package com.example.mobileassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.example.mobileassignment.databinding.ActivityUserProfileBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference


class UserProfile : AppCompatActivity() {
    private lateinit var binding : ActivityUserProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        firebaseAuth = FirebaseAuth.getInstance()
        val binding: ActivityUserProfileBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_user_profile)


        val sharedPref = getSharedPreferences("addName",Context.MODE_PRIVATE)

        val email = sharedPref.getString("email","default value")
        binding.tvUserEmail.text = email.toString()
        binding.btnLogout.setOnClickListener(){
            firebaseAuth.signOut()
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }

        binding.btnBooking.setOnClickListener(){
            val intent = Intent(this, UserBook::class.java)
            startActivity(intent)
        }
        binding.btnHome.setOnClickListener(){
            val intent = Intent(this, UserMainActivity::class.java)
            startActivity(intent)
        }
        binding.btnProfile.setOnClickListener(){
            val intent = Intent(this, UserProfile::class.java)


            startActivity(intent)
        }
    }
}