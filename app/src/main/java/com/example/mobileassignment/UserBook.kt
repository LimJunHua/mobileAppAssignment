package com.example.mobileassignment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.widget.DatePicker
import android.app.AlertDialog
import com.example.mobileassignment.databinding.ActivityUserBookBinding

class UserBook: AppCompatActivity() {
    private lateinit var binding: ActivityUserBookBinding
    private lateinit var datePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_book)



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


//                if(R.id.spinVenue == 0){
//                    binding.tvClinicAddress.text = String.format("4, Jalan 5/4c, Taman Desa Melawati, 53100 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
//                } else if (R.id.spinVenue == 1){
//                    binding.tvClinicAddress.text = String.format("No 17-G, Plaza, Jalan Maju Ria 2, Wangsa Maju, 53300 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
//                } else  if (R.id.spinVenue == 2){
//                    binding.tvClinicAddress.text = String.format("144, Jln Kepong, Pekan Kepong, 52100 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur")
//                } else {
//                    binding.tvClinicAddress.text = String.format("Please select a venue")
//                }

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

}