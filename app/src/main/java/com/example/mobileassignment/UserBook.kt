package com.example.mobileassignment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.widget.*
import com.example.mobileassignment.databinding.ActivityUserBookBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class UserBook : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: ActivityUserBookBinding
    private lateinit var databases: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var datePicker: DatePicker
    val cal: Calendar = Calendar.getInstance()
    val selectedcal: Calendar = Calendar.getInstance()

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
        //cancel booking

        //Admin (doctor) -> can view the booked timeslot and call/accept the booking?
        //Admin (doctor) -> can view activity log (see the doctor actions -> )

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

        val time = arrayOf("8am - 9am","9am - 10am","10am - 11am","11am - 12pm","1pm - 2pm","2pm - 3pm","3pm - 4pm","4pm - 5pm")
        val adapterrr = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, time)
        binding.spinTime.adapter = adapterrr
        binding.spinTime.onItemSelectedListener= object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

//        when (venue){
//            arrayOf("Klinik We-Care") -> binding.tvClinicAddress.text = String.format("4, Jalan 5/4c, Taman Desa Melawati, 53100 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
//            arrayOf("Klinik Vitality") -> binding.tvClinicAddress.text = String.format("No 17-G, Plaza, Jalan Maju Ria 2, Wangsa Maju, 53300 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
//            arrayOf("Klinik Utama") -> binding.tvClinicAddress.text = String.format("144, Jln Kepong, Pekan Kepong, 52100 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
//        }

        //book the appointment
        binding.btnBook2.setOnClickListener() {
            val bundle = intent.extras

            var name = binding.tvBookingName.text.toString()
            val reason = binding.spinReason.selectedItem.toString()
            val venue = binding.spinVenue.selectedItem.toString()
            val dateAndTime = binding.tvTimePicker.text.toString()
            val time = binding.spinTime.selectedItem.toString()

            val appointment = bookings(name,reason, venue, dateAndTime, time)

                myRefs.child(searchEmail).setValue(appointment).addOnCompleteListener() {

                    if (it.isSuccessful) {
                        Toast.makeText(applicationContext, "Booking Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, UserMainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Already have a Booking", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, UserMainActivity::class.java)
                    }
                }

            databases = FirebaseDatabase.getInstance().getReference("bookings")


        }

    }


//    private fun save(){
//        val spinReason = binding.spinReason.selectedItemPosition.toString()
//        val spinVenue = binding.spinVenue.selectedItemPosition.toString()
//        val tvDate = binding.tvDate.text.toString()
//
//
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("bookingAppointment")
//        myRef.child("booking").get().addOnSuccessListener {
//            Toast.makeText(applicationContext, "Booking is successful!", Toast.LENGTH_LONG).show()
//        }
//            .addOnFailureListener {
//                Toast.makeText(applicationContext, "Booking is failed", Toast.LENGTH_LONG).show()
//            }
//
//    }


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