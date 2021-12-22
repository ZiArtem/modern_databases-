package com.example.modern_databases.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.modern_databases.R
import com.example.modern_databases.data.entities.UserInformation
import com.example.modern_databases.viewmodel.PrViewModel

class CompleteRegistryActivity : AppCompatActivity() {
    private lateinit var email: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var location: TextView
    private lateinit var complete: Button
    private lateinit var fillLater: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_registry)

        val intent = intent
        var id_ad = intent.getIntExtra("id_ad", -1)

        email = findViewById(R.id.emailField)
        phoneNumber = findViewById(R.id.phoneField)
        location = findViewById(R.id.locationField)
        complete = findViewById(R.id.completeReg)
        fillLater = findViewById(R.id.fillLater)

        complete.setOnClickListener {
            CompleteRegistry(id_ad)
        }
        fillLater.setOnClickListener {
            FillInfoLater(id_ad)
        }
    }

    private fun CompleteRegistry(id: Int) {
        if (email.text.isNotEmpty() && phoneNumber.text.isNotEmpty() && location.text.isNotEmpty()) {
            val mUserViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            ).get(PrViewModel::class.java)

            Thread(Runnable {
                val userInfo = mUserViewModel.getUserInformation(id)
                mUserViewModel.updateUserInfo(
                    UserInformation(
                        userInfo[0].id_info,
                        phoneNumber.text.toString(),
                        email.text.toString(),
                        userInfo[0].date_registry,
                        location.text.toString(),
                        userInfo[0].image_user,
                        userInfo[0].id_user_
                    ))
            }).start()

            FillInfoLater(id)
        } else {
            Toast.makeText(applicationContext, "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun FillInfoLater(id: Int) {
        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putInt("id_user", id)
            putBoolean("is_checked", true)
        }.apply()

        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("id_ad", id)
        startActivity(intent)
        overridePendingTransition(0, 0);
        finish()
    }
}