package com.example.mobileassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.mobileassignment.databinding.ActivityUserMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserMainActivity : AppCompatActivity(){
    private lateinit var binding:ActivityUserMainBinding
    private lateinit var databases: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_main)


        val sharedPref = getSharedPreferences("addName",Context.MODE_PRIVATE)

        val userEmail = sharedPref.getString("email","default value")
        val searchEmail = userEmail?.replace('.', ',').toString()
        databases = FirebaseDatabase.getInstance().getReference("appointment")
        databases.child(searchEmail).get().addOnSuccessListener {

            if (it.exists()) {
                binding.tvReason.text = it.child("reason").value.toString()
                binding.tvVenue.text = it.child("venue").value.toString()
                binding.tvDateTime.text = it.child("dateAndTime").value.toString()
            }
        }


        binding.btnBook.setOnClickListener(){

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
