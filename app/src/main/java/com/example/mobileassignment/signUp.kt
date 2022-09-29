package com.example.mobileassignment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase

class signUp : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //databinding
        val binding: ActivitySignUpBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_sign_up)

        //database access

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")

        var btnRegister = binding.btnRegister


          btnRegister.setOnClickListener() {
            var validation = true
            val bundle = intent.extras
            val username = binding.signupUsername.text.toString()
            val name = binding.signupName.text.toString()
              val test = binding.tvName
            val email = binding.signupEmail.text.toString()
            val iCNumber = binding.signupICNumber.text.toString()
            val phoneNumber = binding.signupPhoneNumber.text.toString()
            val password = binding.signupPassword.text.toString()
              val test1 = binding.test1.text.toString()
            val comfirmPassword = binding.signupComfirmPassword.text.toString()
            val users = user(username , name ,iCNumber, phoneNumber, email, password )
              myRef.child(name).get()
                  .addOnSuccessListener { result ->
                      if (result != null ) {
                          test.text = result.child("$name").value.toString()
                      }
                  }
              if(username.isEmpty() || name.isEmpty()|| email.isEmpty()|| iCNumber.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || comfirmPassword.isEmpty() ){
                  Toast.makeText(applicationContext, "please fill in all the field", Toast.LENGTH_LONG).show()
              }else if(password.length <9){
                  Toast.makeText(applicationContext, "Password should be more than 9 ", Toast.LENGTH_LONG).show()
              }else if (password != comfirmPassword){
                  Toast.makeText(applicationContext, "Password does not match ", Toast.LENGTH_LONG).show()
              }
              else {

                  myRef.child(users.name).setValue(users)
                      .addOnSuccessListener {
                          Toast.makeText(applicationContext, "Add successful", Toast.LENGTH_LONG)
                              .show()
                      }
                      .addOnFailureListener {
                          Toast.makeText(applicationContext, "Add failed", Toast.LENGTH_LONG).show()
                      }
              }

          }

    }

        // writeNewUser(username, name,  email, ICNumber, password)




    //if (bundle != null){
    //  tv1.text = " ${bundle.getString("username")}"
    // tv2.text = "${bundle.getString("password")}"

    //}
        //Register Button method end
}




