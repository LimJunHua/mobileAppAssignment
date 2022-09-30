package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mobileassignment.databinding.ActivityUserMainBinding
import com.google.firebase.database.FirebaseDatabase

class UserMainActivity : AppCompatActivity(){
    private lateinit var binding:ActivityUserMainBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_main)

//        setContentView(R.layout.activity_user_main)
//        val binding: ActivityUserMainBinding = DataBindingUtil.setContentView(
//            this, R.layout.activity_user_main)





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
