package com.example.mobileassignment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mobileassignment.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class signUp : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private lateinit var databases : DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
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

            var name = binding.signupName.text.toString()
            val email = binding.signupEmail.text.toString()
            val iCNumber = binding.signupICNumber.text.toString()
            val phoneNumber = binding.signupPhoneNumber.text.toString()
            val password = binding.signupPassword.text.toString()
            val comfirmPassword = binding.signupComfirmPassword.text.toString()

              var tvName = binding.tvName



              val users = user(name ,iCNumber, phoneNumber, email, password )
              databases = FirebaseDatabase.getInstance().getReference("users")

              val myIntent = Intent(this, MainActivity::class.java)

              if( name.isEmpty()|| email.isEmpty()|| iCNumber.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || comfirmPassword.isEmpty() ){
                  Toast.makeText(applicationContext, "please fill in all the field", Toast.LENGTH_LONG).show()
              }else if(password.length <9){
                  Toast.makeText(applicationContext, "Password should be more than 9 ", Toast.LENGTH_LONG).show()
              }else if (password != comfirmPassword){
                  Toast.makeText(applicationContext, "Password does not match ", Toast.LENGTH_LONG).show()
              }
              else{


                  firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(){
                      if(it.isSuccessful){
                          myRef.child(users.name).setValue(users)

                          startActivity(myIntent)

                     }else {
                          Toast.makeText(applicationContext, "This email has been registered, please try again ", Toast.LENGTH_LONG).show()

                      }

                  }




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




