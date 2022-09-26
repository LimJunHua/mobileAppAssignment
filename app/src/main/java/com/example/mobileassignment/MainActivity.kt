package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        val signup = binding.tvRegister
        val username = binding.dataUsername
        val password = binding.dataPassword
        val forgetPasswords = binding.tvForgetPassword

        signup.setOnClickListener {
            val myIntent = Intent(this,signUp::class.java )
            //passdata
            val bundle = Bundle()

            bundle.putString("username", username.text.toString())
            bundle.putString("password", password.text.toString())
            myIntent.putExtras(bundle)

            startActivity(myIntent)
        }
        forgetPasswords.setOnClickListener {
            val myIntent = Intent(this,forgetPassword::class.java )
            startActivity(myIntent)
        }
        val btnTest = binding.btnLogin



    }


}