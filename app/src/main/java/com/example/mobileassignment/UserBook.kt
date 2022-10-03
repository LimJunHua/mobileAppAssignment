package com.example.mobileassignment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.app.TimePickerDialog
import android.content.Context
import android.widget.*
import com.example.mobileassignment.databinding.ActivityUserBookBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class UserBook : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: ActivityUserBookBinding
    private lateinit var databases: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var datePicker: DatePicker
    val cal: Calendar = Calendar.getInstance()
    val database = FirebaseDatabase.getInstance()
    val selectedcal: Calendar = Calendar.getInstance()
    val datePath = database.getReference("Booked")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_book)
        firebaseAuth = FirebaseAuth.getInstance()

        val database = FirebaseDatabase.getInstance()
        val myRefs = database.getReference("appointment")
        //navigation
        binding.btnHome.setOnClickListener() {
            val intent = Intent(this, UserMainActivity::class.java)
            startActivity(intent)
        }

        //get date and time
        pickDate()

        //use a secret code to share preference
        val sharedPref = getSharedPreferences("addName", Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("email", "default value")
        val searchEmail = userEmail?.replace('.', ',').toString()
        var cal = Calendar.getInstance()

        //UserBooking improvements
        //limit one day booking to 10 times only
        //limit appointment time from 8 am - 5 pm -> done
        //day only limited from mon to fri -> done
        //cancel booking -> done

        //get reason and venue
        databases = FirebaseDatabase.getInstance().getReference("users")
        databases.child(searchEmail).get().addOnSuccessListener {

            if (it.exists()) {
                binding.tvBookingName.text = it.child("name").value.toString()
            }
        }



        val reason = arrayOf("Medical Checkup", "Feeling Unwell")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reason)
        binding.spinReason.adapter = adapter
        val spinReason = binding.spinReason

        val venue = arrayOf("Klinik We-Care", "Klinik Vitality", "Klinik Utama")
        val adapt = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, venue)
        binding.spinVenue.adapter = adapt

        val spinVenue = binding.spinVenue

        //book the appointment
        binding.btnBook2.setOnClickListener() {
            val bundle = intent.extras

            var name = binding.tvBookingName.text.toString()
            val reason = binding.spinReason.selectedItem.toString()
            val venue = binding.spinVenue.selectedItem.toString()
            val dateAndTime = binding.tvTimePicker.text.toString()
            val time = binding.spinTime.selectedItem.toString()
            var dbselectedtime = ""

//            val booleanTime = findAvailableTimeSlot(dateAndTime, time)
//            if (booleanTime) {
                val appointment = bookings(name, reason, venue, dateAndTime, time)
//                myRefs.child(searchEmail).setValue(appointment).addOnCompleteListener() {
//
//                    if (it.isSuccessful) {
//                        Toast.makeText(applicationContext, "Booking Success", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this, UserMainActivity::class.java)
//                        startActivity(intent)
//                    } else {
//                        Toast.makeText(applicationContext, "Already have a Booking", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this, UserMainActivity::class.java)
//                    }
//                }
            myRefs.child(searchEmail).setValue(appointment).addOnCompleteListener() {

                if (it.isSuccessful) {
                    datePath.get().addOnSuccessListener {
                        if (it.exists()) {
                            if (it.child(dateAndTime).exists()) {
                                dbselectedtime = it.child(dateAndTime).value.toString()
                                val selectedTime = "$dbselectedtime.$time"
                                datePath.child(dateAndTime).setValue(selectedTime).addOnCompleteListener() {

                                }.addOnFailureListener{
                                    Toast.makeText(applicationContext, it.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                datePath.child(dateAndTime).setValue(time).addOnCompleteListener() {

                                }.addOnFailureListener{
                                    Toast.makeText(applicationContext, it.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            datePath.child(dateAndTime).setValue(time).addOnCompleteListener() {

                            }.addOnFailureListener{
                                Toast.makeText(applicationContext, it.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }.addOnFailureListener{
                        Toast.makeText(applicationContext, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                        Toast.makeText(applicationContext, "Booking Success!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, UserMainActivity::class.java)
                        startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Already have a Booking", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, UserMainActivity::class.java)
                }
            }


        }

    }

    private fun findAvailableTimeSlot(dateAndTime: String, time: String):Boolean {
        val database = FirebaseDatabase.getInstance()
        val myRefs = database.getReference("Booked")
        var bookedTime = ""
        var availableList = arrayListOf<String>()
        myRefs.child(dateAndTime).get()
            .addOnSuccessListener { result ->
                if (result != null) {
                    bookedTime=result.value.toString()

                }
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "Failed to read.", Toast.LENGTH_LONG).show()
            }
        if(bookedTime.isNotEmpty()){
            val bookedArray = bookedTime.split(".").toTypedArray()
            for (retrieveTime in bookedArray){
                if(retrieveTime.contains(time)) {

                    return false
                }
            }
        }
        return true
    }




    //TimePicker
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0
    var saturday = 0
    var sunday = 0

    var savedday = 0
    var savedmonth = 0
    var savedyear = 0
    var savedHour = 0
    var savedMinute = 0

    var displaymonth = 0

    private fun pickDate() {
        binding.btnTimePicker.setOnClickListener() {
            getDateTimeCalendar()

            DatePickerDialog(this, this, year, month, day).show()
        }

    }

    private fun getDateTimeCalendar() {
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
        saturday = cal.get(Calendar.SATURDAY)
        sunday = cal.get(Calendar.SUNDAY)

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute


    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedday = dayOfMonth
        savedmonth = month
        savedyear = year
        displaymonth = savedmonth + 1

        if (savedyear == cal.get(Calendar.YEAR)) {
            if (savedmonth >= cal.get(Calendar.MONTH)) {
                if (savedday >= cal.get(Calendar.DAY_OF_MONTH)) {

                    selectedcal.set(savedyear, savedmonth, savedday)
                    if (selectedcal.get(Calendar.DAY_OF_WEEK) != 1 && selectedcal.get(Calendar.DAY_OF_WEEK) != 7) {
                        binding.tvTimePicker.text = "$savedday-$displaymonth-$savedyear"
                        var selectedtimearray: List<String>
                        var dbselectedtime = ""

                        var select = ArrayList<String>()
                        select.add("8am - 9am")
                        select.add("9am - 10am")
                        select.add("10am - 11am")
                        select.add("11am - 12pm")
                        select.add("1pm - 2pm")
                        select.add("2pm - 3pm")
                        select.add("3pm - 4pm")
                        select.add("4pm - 5pm")

                        datePath.get().addOnSuccessListener {
                            if (it.exists()) {
                                if (it.child("$savedday-$displaymonth-$savedyear").exists()) {
                                    dbselectedtime = it.child("$savedday-$displaymonth-$savedyear").value.toString()
                                    selectedtimearray = dbselectedtime.split("\\.".toRegex())
                                    for (item in selectedtimearray) {
                                        select.remove(item)
                                    }

                                    var adapterrr = ArrayAdapter(this, android.R.layout.simple_list_item_1, select)
                                    binding.spinTime.adapter = adapterrr
                                    binding.spinTime.setSelection(0)
                                }else {
                                    var adapterrr = ArrayAdapter(this, android.R.layout.simple_list_item_1, select)
                                    binding.spinTime.adapter = adapterrr
                                    binding.spinTime.setSelection(0)
                                }
                            }
                            else {
                                var adapterrr = ArrayAdapter(this, android.R.layout.simple_list_item_1, select)
                                binding.spinTime.adapter = adapterrr
                                binding.spinTime.setSelection(0)
                            }
                        }.addOnFailureListener{
                            Toast.makeText(applicationContext, it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "You can only select Monday to Friday.", Toast.LENGTH_SHORT)
                            .show()
                    }

                } else {
                    Toast.makeText(applicationContext, "Invalid day chosen, please choose date start from today.", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Invalid month chosen, please choose date start from today.", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(applicationContext, "Invalid year chosen, please choose date start from today.", Toast.LENGTH_SHORT).show()
        }

    }

}