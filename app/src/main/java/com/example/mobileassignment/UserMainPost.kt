package com.example.mobileassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.databinding.ActivityUserMainPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserMainPost : AppCompatActivity() {
    private lateinit var databases : DatabaseReference

    private lateinit var  userArrayList: ArrayList<post>
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        //set time
        val format: DateFormat = SimpleDateFormat("YYYYMMdd_hhmm a")
        val d = System.currentTimeMillis()
        val date: String = format.format(Date(d))


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main_post)
        val binding: ActivityUserMainPostBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_user_main_post)
        val settingPic = binding.settingIcon
        val post=binding.btnPostPost
        val chat  =binding.btnPostMusic
        val music =binding.btnPostMusic
        settingPic.setOnClickListener(){
            val myIntent = Intent(this,UserProfile::class.java)
            startActivity(myIntent)
        }
        post.setOnClickListener(){
            val myIntent = Intent(this,SettingPage::class.java)
            startActivity(myIntent)
        }
        chat.setOnClickListener(){
            val myIntent = Intent(this,SettingPage::class.java)
            startActivity(myIntent)
        }
        music.setOnClickListener(){
            val myIntent = Intent(this,SettingPage::class.java)
            startActivity(myIntent)
        }

        val recyclerview  =binding.postContent
        //recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<post>()
        //userRecyclerView.layoutManager = LinearLayoutManager(this)
        //userRecyclerView.setHasFixedSize(true)
        //userArrayList = arrayListOf<post>()
        //getUserData()






        }
}




