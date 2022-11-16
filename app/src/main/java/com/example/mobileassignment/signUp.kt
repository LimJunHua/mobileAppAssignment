package com.example.mobileassignment

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //databinding
        val binding: ActivitySignUpBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_sign_up)

        //database access
        val database = FirebaseDatabase.getInstance()
        val myRefs = database.getReference("users")

          binding.btnRegister.setOnClickListener() {
            val bundle = intent.extras

            var name = binding.signupName.text.toString()
            var rawEmail = binding.signupEmail.text.toString()
            val iCNumber = binding.signupICNumber.text.toString()
            val phoneNumber = binding.signupPhoneNumber.text.toString()
            val password = binding.signupPassword.text.toString()
            val comfirmPassword = binding.signupComfirmPassword.text.toString()


              val email= rawEmail.replace('.', ',')

              val users = user(name ,iCNumber, phoneNumber, email )


              val myIntent = Intent(this, verificationRegister::class.java)

              if( name.isEmpty()|| email.isEmpty()|| iCNumber.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || comfirmPassword.isEmpty() ){
                  Toast.makeText(applicationContext, "please fill in all the field", Toast.LENGTH_LONG).show()
              }else if(password.length <9){
                  Toast.makeText(applicationContext, "Password should be more than 9 ", Toast.LENGTH_LONG).show()
              }else if (password != comfirmPassword){
                  Toast.makeText(applicationContext, "Password does not match ", Toast.LENGTH_LONG).show()
              }
              else{
                  firebaseAuth.createUserWithEmailAndPassword(rawEmail, password).addOnCompleteListener(){
                      if(it.isSuccessful){
                          val sharedPref = getSharedPreferences("addName", Context.MODE_PRIVATE)
                          var verificationCode = (10000..99999).random()
                          var edit = sharedPref.edit()
                          edit.putString("email",email)
                          edit.putString("name",name)
                          edit.putString("phoneNumber",phoneNumber)
                          edit.putString("iCNumber",iCNumber)
                          edit.putString("password",password)
                          edit.putInt("verificationCode",verificationCode)
                          edit.commit()
                          var sendEmail = Intent(Intent.ACTION_SEND)
                          sendEmail.data = Uri.parse("Mail to:")
                          sendEmail.type = "text/plain"
                          sendEmail.putExtra(Intent.EXTRA_EMAIL,rawEmail)
                          sendEmail.putExtra(Intent.EXTRA_SUBJECT,"Verification Code")
                          sendEmail.putExtra(Intent.EXTRA_TEXT,rawEmail)



                          myRefs.child(users.email).setValue(users)
                          Toast.makeText(applicationContext, "Register Completed ", Toast.LENGTH_SHORT).show()
                          startActivity(myIntent)
                     }else {
                          Toast.makeText(applicationContext, "This email has been registered, please try again ", Toast.LENGTH_LONG).show()
                      }

                  }




              }
        }

        binding.btnReturn.setOnClickListener(){
            val myIntent = Intent(this,MainActivity::class.java)
            startActivity(myIntent)
        }




    }
    fun  encodeUserEmail (userEmail :String) :String{
        return userEmail.replace(".", ",");
    }
    fun  decodeUserEmail (userEmail :String) :String{
        return userEmail.replace(",", ".");
    }

    // writeNewUser(username, name,  email, ICNumber, password)




    //if (bundle != null){
    //  tv1.text = " ${bundle.getString("username")}"
    // tv2.text = "${bundle.getString("password")}"

    //}
        //Register Button method end
}




