package com.example.mobileassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivitySignUpBinding
import com.google.firebase.database.FirebaseDatabase
import com.example.mobileassignment.UserHelperClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class signUp : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val binding: ActivitySignUpBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_sign_up)
        //receive data
        database = Firebase.database.reference
        val btnRegister = binding.btnRegister
        fun writeNewUser(username: String, name: String,  email: String,ICNumber: String,password: String) {
            val helperClass = UserHelperClass(username, name,  email, ICNumber, password)
            database.child("users").setValue(helperClass)

        }
          btnRegister.setOnClickListener() {
            val bundle = intent.extras
            val username = binding.signupUsername.toString()
            val name = binding.signupName.toString()
            val email = binding.signupEmail.toString()
            val ICNumber = binding.signupICNumber.toString()
            val password = binding.signupPassword.toString()
            val comfirmPassword = binding.signupComfirmPassword.toString()
            val rootNode = FirebaseDatabase.getInstance();
            val reference = rootNode.getReference("users");
            writeNewUser(username, name,  email, ICNumber, password)




            }
        //Register Button method end
    }//onCreate Method End

        //if (bundle != null){
          //  tv1.text = " ${bundle.getString("username")}"
           // tv2.text = "${bundle.getString("password")}"

        //}

    }
