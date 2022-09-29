package com.example.mobileassignment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivitySignUpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class signUp : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private lateinit var databases : DatabaseReference

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
            val bundle = intent.extras
            val username = binding.signupUsername.text.toString()
            var name = binding.signupName.text.toString()
            val email = binding.signupEmail.text.toString()
            val iCNumber = binding.signupICNumber.text.toString()
            val phoneNumber = binding.signupPhoneNumber.text.toString()
            val password = binding.signupPassword.text.toString()
            val comfirmPassword = binding.signupComfirmPassword.text.toString()
              var validNames ="123"
              var validUsernames=""
              var validICNumbers=""
              var validPhoneNumbers=""
              var validEmails=""
              var tvName = binding.tvName
              var tvUsername = binding.tvUsername


              val users = user(username , name ,iCNumber, phoneNumber, email, password )
              databases = FirebaseDatabase.getInstance().getReference("users")
              databases.child(name).get().addOnSuccessListener {
                  if (it.exists()) {
                      var validName = it.child("name").value.toString()
                      validNames= validName
                      tvUsername .text= validName.toString()

                  }
              }
              databases.child(username).get().addOnSuccessListener {
                  if (it.exists()) {
                      val validUsername = it.child("username").value.toString()
                      validUsernames = validUsername
                  }
              }
              databases.child(iCNumber).get().addOnSuccessListener {
                  if (it.exists()) {
                      val validICNumber= it.child("icnumber").value.toString()
                      validICNumbers = validICNumber
                  }
              }
              databases.child(phoneNumber).get().addOnSuccessListener {
                  if (it.exists()) {
                     val validPhoneNumber = it.child("phoneNumber").value.toString()
                      validPhoneNumbers = validPhoneNumber
                  }
              }
              databases.child(email).get().addOnSuccessListener {
                  if (it.exists()) {
                      val validEmail = it.child("email").value.toString()
                      validEmails = validEmail
                  }
              }

              if(username.isEmpty() || name.isEmpty()|| email.isEmpty()|| iCNumber.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || comfirmPassword.isEmpty() ){
                  Toast.makeText(applicationContext, "please fill in all the field", Toast.LENGTH_LONG).show()
              }else if(password.length <9){
                  Toast.makeText(applicationContext, "Password should be more than 9 ", Toast.LENGTH_LONG).show()
              }else if (password != comfirmPassword){
                  Toast.makeText(applicationContext, "Password does not match ", Toast.LENGTH_LONG).show()
              }
              else{

                  myRef.child(users.username).setValue(users)
                      .addOnSuccessListener {
                          Toast.makeText(applicationContext, "Add successful", Toast.LENGTH_LONG)
                              .show()
                      }
                      .addOnFailureListener {
                          Toast.makeText(applicationContext, "Add failed", Toast.LENGTH_LONG).show()
                      }

                 val myIntent = Intent(this, pendingVerification::class.java)
                  //passdata
                  val bundle = Bundle()

                  bundle.putString("username", username)
                  bundle.putString("name", name)
                  bundle.putString("iCNumber", iCNumber)
                  bundle.putString("phoneNumber", phoneNumber)
                  bundle.putString("password", password)
                  bundle.putString("email", email)

                  myIntent.putExtras(bundle)

                  startActivity(myIntent)

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




