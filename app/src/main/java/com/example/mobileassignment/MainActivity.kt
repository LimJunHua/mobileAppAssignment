package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var databases : DatabaseReference
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        val signup = binding.tvRegister
        val username = binding.dataUsername.text.toString()
        val password = binding.dataPassword.text.toString()
        val forgetPasswords = binding.tvForgetPassword

        signup.setOnClickListener {
            val myIntent = Intent(this,signUp::class.java )
            startActivity(myIntent)
        }
        forgetPasswords.setOnClickListener {
            val myIntent = Intent(this,forgetPassword::class.java )
            startActivity(myIntent)
        }
        val btnLogin = binding.btnLogin

        btnLogin.setOnClickListener(){
            databases = FirebaseDatabase.getInstance().getReference("users")
            val username = binding.dataUsername.text.toString()
            databases = FirebaseDatabase.getInstance().getReference("users")
            databases.child(username).get().addOnSuccessListener {
                if (it.exists()) {
                    var passwords = it.child("password").value.toString()
                    if(password.equals(passwords)){
                        val myIntent = Intent(this,forgetPassword::class.java )
                        startActivity(myIntent)
                    }


                }
            }

        }

        //passdata
        // val bundle = Bundle()

        //bundle.putString("username", username.text.toString())
        // bundle.putString("password", password.text.toString())
        //myIntent.putExtras(bundle)

    }


}