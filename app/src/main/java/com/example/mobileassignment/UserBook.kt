package com.example.mobileassignment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.widget.DatePicker
import android.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.mobileassignment.databinding.ActivityUserBookBinding
import java.text.SimpleDateFormat
import java.util.*

class UserBook: AppCompatActivity() {
    private lateinit var binding: ActivityUserBookBinding
    private lateinit var datePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_book)

        var cal = Calendar.getInstance()

        // get the references from layout file

        this.binding.tvDate
        this.binding.btnDate
        binding.tvDate.text = "--/--/----"

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        binding.btnDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@UserBook,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        val reason = arrayOf("Medical Checkup", "Feeling Unwell")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reason)
        binding.spinReason.adapter = adapter

        val venue = arrayOf("Klinik We-Care", "Klinik Vitality", "Klinik Utama")
        val adapt = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, venue)
        binding.spinVenue.adapter = adapt

        when (venue){
            arrayOf("Klinik We-Care") -> binding.tvClinicAddress.text = String.format("4, Jalan 5/4c, Taman Desa Melawati, 53100 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
            arrayOf("Klinik Vitality") -> binding.tvClinicAddress.text = String.format("No 17-G, Plaza, Jalan Maju Ria 2, Wangsa Maju, 53300 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
            arrayOf("Klinik Utama") -> binding.tvClinicAddress.text = String.format("144, Jln Kepong, Pekan Kepong, 52100 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
        }

        binding.btnHome.setOnClickListener(){
            val intent = Intent(this, UserMainActivity::class.java)
            startActivity(intent)
        }



        binding.btnProfile.setOnClickListener(){
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }

        binding.btnBook2.setOnClickListener(){

        }





    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        var cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvDate.text = sdf.format(cal.getTime())
    }

}