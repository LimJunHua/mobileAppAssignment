package com.example.mobileassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivityUserProfileBinding

class UserProfile : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_profile)

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