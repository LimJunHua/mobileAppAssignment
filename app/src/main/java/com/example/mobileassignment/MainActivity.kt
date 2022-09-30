package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var databases: DatabaseReference
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        val signup = binding.tvRegister
        val forgetPasswords = binding.tvForgetPassword
        val btnLogin = binding.btnLogin

        firebaseAuth = FirebaseAuth.getInstance()

        //forward
        signup.setOnClickListener {
            val myIntent = Intent(this, signUp::class.java)
            startActivity(myIntent)
        }
        forgetPasswords.setOnClickListener {
            val myIntent = Intent(this, UserBook::class.java)
            startActivity(myIntent)
        }


        btnLogin.setOnClickListener() {

            val email = binding.dataEmail.text.toString()
            val password = binding.dataPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val myIntent = Intent(this, UserMainActivity::class.java)
                        val bundle = Bundle()
                        Toast.makeText(applicationContext, "Welcome to St4yAlive ", Toast.LENGTH_SHORT).show()
                        startActivity(myIntent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }



        }




    }
    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
        //passdata
        // val bundle = Bundle()

        //bundle.putString("username", username.text.toString())
        // bundle.putString("password", password.text.toString())
        //myIntent.putExtras(bundle)


}



