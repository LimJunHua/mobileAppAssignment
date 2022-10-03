package com.example.mobileassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.databinding.ActivityUserMainBinding
import com.google.firebase.database.*
import androidx.appcompat.app.AlertDialog

class UserMainActivity : AppCompatActivity(){
    private lateinit var binding:ActivityUserMainBinding
    private lateinit var databases: DatabaseReference
    private lateinit var bookingRecycler: RecyclerView
    private lateinit var bookingArrayList: ArrayList<bookings>
    private lateinit var builder : AlertDialog.Builder
    private lateinit var btnBookCancel : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_main)
//        binding.recyclerBooking.adapter()
        builder = AlertDialog.Builder(this)



        //open a variable
        val sharedPref = getSharedPreferences("addName",Context.MODE_PRIVATE)

        //use "email" as primary key to access the data
        val userEmail = sharedPref.getString("email","default value")

        //replace "." with "," because Firebase cannot allow "."
        val searchEmail = userEmail?.replace('.', ',').toString()

        databases = FirebaseDatabase.getInstance().getReference("users")
        val databaseses = FirebaseDatabase.getInstance()
        val myReffs = databaseses.getReference("users")
        databases.child(searchEmail).get().addOnSuccessListener {

            if (it.exists()) {
                binding.tvDisplayName.text = it.child("name").value.toString()
            }
        }

        databases = FirebaseDatabase.getInstance().getReference("appointment")
        val database = FirebaseDatabase.getInstance()
        val myRefs = database.getReference("appointment")
        databases.child(searchEmail).get().addOnSuccessListener {

            if (it.exists()) {
                binding.tvViewName.text = it.child("name").value.toString()
                binding.tvViewVenue.text = it.child("venue").value.toString()
                binding.tvViewDate.text = it.child("dateAndTime").value.toString()
                binding.tvViewReason.text = it.child("reason").value.toString()
                binding.tvViewTime.text = it.child("time").value.toString()
            }
        }

        binding.constraintMainBooking.setOnClickListener(){
            val bookName = binding.tvViewName.text.toString()
            val bookVenue = binding.tvViewVenue.text.toString()
            val bookDate = binding.tvViewDate.text.toString()
            val bookReason = binding.tvViewReason.text.toString()
            val bookTime = binding.tvViewTime.text.toString()
            if (bookName.isEmpty() && bookVenue.isEmpty() && bookDate.isEmpty() && bookReason.isEmpty()
                && bookTime.isEmpty()) {

            } else {

            }
        }

        binding.btnBookCancel.setOnClickListener(){

            var bookName = binding.tvViewName.text.toString()
            var bookVenue = binding.tvViewVenue.text.toString()
            var bookDate = binding.tvViewDate.text.toString()
            var bookReason = binding.tvViewReason.text.toString()
            var bookTime = binding.tvViewTime.text.toString()
            if (bookName.isNotEmpty() && bookVenue.isNotEmpty() && bookDate.isNotEmpty() && bookReason.isNotEmpty()
                && bookTime.isNotEmpty()) {
                builder.setTitle("OH SNAP")
                    .setMessage("Do you want to cancel?")
                    .setCancelable(true)
                    .setPositiveButton("Yes") { dialogInterface, it -> deleteBooking(searchEmail) }
                    .setNegativeButton("No") { dialogInterface, it -> dialogInterface.cancel() }
                    .show()
            } else {
                Toast.makeText(applicationContext, "You have no bookings!", Toast.LENGTH_SHORT).show()
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

    private fun deleteBooking(searchEmail: String) {
        databases = FirebaseDatabase.getInstance().getReference("appointment")
        databases.child(searchEmail).removeValue().addOnSuccessListener {
            Toast.makeText(applicationContext, "Booking is cancelled", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UserMainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener{
            Toast.makeText(applicationContext, "Cancellation is failed", Toast.LENGTH_SHORT).show()
        }

    }


}
