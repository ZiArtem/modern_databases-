package com.example.modern_databases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.UserViewModel

class Activity2 : AppCompatActivity() {
    lateinit var mUserViewModel: UserViewModel
    lateinit var  test: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        test=  findViewById(R.id.test_button)

        test.setOnClickListener { testFunk () }
    }

    private fun testFunk () {
        var ad: Ad = Ad(0, "title test","description test", 80,1,2)
        mUserViewModel.addAd(ad)
        Toast.makeText(applicationContext,"Successful!!!!", Toast.LENGTH_SHORT).show()
    }
}