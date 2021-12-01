package com.example.modern_databases

import android.content.Intent
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
    lateinit var  test2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        test=  findViewById(R.id.test_button)
        test2=  findViewById(R.id.test_button2)

        test.setOnClickListener { testFunk () }
        test2.setOnClickListener { testFunk2 () }
    }

    private fun testFunk () {
        var ad: Ad = Ad(0, "title test","description test", 80,1,2)
        mUserViewModel.addAd(ad)
        Toast.makeText(applicationContext,"Successful!!!!", Toast.LENGTH_SHORT).show()
    }

    private fun testFunk2 () {
        Thread(Runnable {
            val ad = mUserViewModel.getAdByIdUser(3)
            if (!ad.isEmpty()) {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        ("ad id 1, title = " + ad[0].title + " description = " + ad[0].description + " price = " + ad[0].price),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(applicationContext, "not ad", Toast.LENGTH_SHORT).show()
                }
            }
        }).start()
    }




}