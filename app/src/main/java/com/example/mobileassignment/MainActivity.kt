package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var databases: DatabaseReference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        val signup = binding.tvRegister
        val forgetPasswords = binding.tvForgetPassword
        signup.setOnClickListener {
            val myIntent = Intent(this, signUp::class.java)
            startActivity(myIntent)
        }
        forgetPasswords.setOnClickListener {
            val myIntent = Intent(this, forgetPassword::class.java)
            startActivity(myIntent)
        }
        val btnLogin = binding.btnLogin

        btnLogin.setOnClickListener() {
            var username = binding.dataUsername.text.toString()
            val password = binding.dataPassword.text.toString()
            val tvUsername = binding.tvUsername
            val tvPassword = binding.tvPassword
            if(username.isEmpty() || password.isEmpty()){

                Toast.makeText(applicationContext, "please fill in all the field", Toast.LENGTH_LONG).show()
            }

            databases = FirebaseDatabase.getInstance().getReference("users")
            databases.child(username).get().addOnSuccessListener {
                if (it.exists()) {
                    var validPassword = it.child("password").value.toString()
                    if(password.equals(validPassword)){

                        val myIntent = Intent(this, UserMainActivity::class.java)
                        Toast.makeText(applicationContext, "Welcome to HealthCare sdn bhd", Toast.LENGTH_LONG).show()

                        startActivity(myIntent)
                    }else{
                        Toast.makeText(applicationContext, "password wrong", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(applicationContext, "username not exist", Toast.LENGTH_LONG).show()

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



