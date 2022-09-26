package com.example.mobileassignment

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivitySignUpBinding


class signUp : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val binding: ActivitySignUpBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_sign_up)
        //receive data
        val bundle = intent.extras
        val tv1 = binding.tv1
        val tv2 = binding.tv2

        if (bundle != null){
            tv1.text = " ${bundle.getString("username")}"
            tv2.text = "${bundle.getString("password")}"

        }

    }
}