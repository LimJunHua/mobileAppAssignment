package com.example.mobileassignment

import android.media.Image
import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mobileassignment.databinding.ActivityUserMainBinding

class UserMainActivity : AppCompatActivity(){
    private lateinit var binding:ActivityUserMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_main)

//        setContentView(R.layout.activity_user_main)
//        val binding: ActivityUserMainBinding = DataBindingUtil.setContentView(
//            this, R.layout.activity_user_main)


        val imgHome = binding.imgHome
        val imgBooking = binding.imgBooking
        val imgProfile = binding.imgProfile

        imgHome.setOnClickListener(){
            replaceFragment(UserHomeFragment())
        }
        imgBooking.setOnClickListener(){
            replaceFragment(UserBookingFragment())
        }
        imgProfile.setOnClickListener(){
            replaceFragment(UserHomeFragment())
        }




    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView2,fragment)
        fragmentTransaction.commit()
    }
}
