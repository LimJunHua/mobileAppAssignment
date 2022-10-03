package com.example.mobileassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.databinding.ActivityUserMainBinding
import com.google.firebase.database.*

class UserMainActivity : AppCompatActivity(){
    private lateinit var binding:ActivityUserMainBinding
    private lateinit var databases: DatabaseReference
    private lateinit var bookingRecycler: RecyclerView
    private lateinit var bookingArrayList: ArrayList<bookings>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_main)
//        binding.recyclerBooking.adapter()

        //open a variable
        val sharedPref = getSharedPreferences("addName",Context.MODE_PRIVATE)

        //use "email" as primary key to access the data
        val userEmail = sharedPref.getString("email","default value")

        //replace "." with "," because FIrebase cannot allow "."
        val searchEmail = userEmail?.replace('.', ',').toString()
        databases = FirebaseDatabase.getInstance().getReference("appointment")
        databases.child(searchEmail).get().addOnSuccessListener {

            if (it.exists()) {

                binding.tvViewName.text = it.child("name").value.toString()
                binding.tvViewVenue.text = it.child("venue").value.toString()
                binding.tvViewDate.text = it.child("dateAndTime").value.toString()
                binding.tvViewTime.text = it.child("time").value.toString()
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

        binding.btnBooking.setOnClickListener(){
            val intent = Intent(this, AboutUs::class.java)
            startActivity(intent)
        }



        binding.btnProfile.setOnClickListener(){
            val intent = Intent(this, UserProfile::class.java)


            startActivity(intent)
        }




    }




}
