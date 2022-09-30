package com.example.mobileassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivityChangeProfileBinding



import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class changeProfile : AppCompatActivity() {
    private lateinit var databases : DatabaseReference
    private lateinit var binding: ActivityChangeProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile)
        val binding: ActivityChangeProfileBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_change_profile
        )
        val database = FirebaseDatabase.getInstance()
        val myRefs = database.getReference("users")
        val sharedPref = getSharedPreferences("addName", Context.MODE_PRIVATE)

        val userEmail = sharedPref.getString("email","default value")
        val searchEmail = userEmail?.replace('.', ',').toString()
        databases = FirebaseDatabase.getInstance().getReference("users")
        databases.child(searchEmail).get().addOnSuccessListener {
            if (it.exists()) {
                binding.tvChangeName.setText(it.child("name").value.toString())
                binding.tvChangeICNumber.setText(it.child("icnumber").value.toString())
                binding.tvChangePhoneNumber.setText(it.child("phoneNumber").value.toString())

            }
        }

        binding.btnApply.setOnClickListener(){
            databases.child(searchEmail).get().addOnSuccessListener {
                if (it.exists()) {
                    val email = searchEmail.toString()
                    val name = binding.tvChangeName.text.toString()
                    val iCNumber = binding.tvChangeICNumber.text.toString()
                    val phoneNumber = binding.tvChangePhoneNumber.text.toString()

                    if (email.isEmpty() ||name.isEmpty()||iCNumber.isEmpty()||phoneNumber.isEmpty()){
                        Toast.makeText(applicationContext, "cannot leave any field blank ", Toast.LENGTH_SHORT).show()
                    }else{
                        val users = user(name ,iCNumber, phoneNumber, email  )
                        myRefs.child(users.email).setValue(users)

                        val myIntent = Intent(this, UserProfile::class.java)
                        startActivity(myIntent)
                    }
                }
            }


        }
        binding.btnCancel.setOnClickListener(){
            val myIntent = Intent(this, UserProfile::class.java)
            startActivity(myIntent)
        }
    }
}