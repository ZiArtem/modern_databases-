package com.example.modern_databases.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.modern_databases.R

class CompleteRegistryActivity : AppCompatActivity() {

    private lateinit var email: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var location: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_complete_registry)

        email = findViewById(R.id.emailField)
        phoneNumber = findViewById(R.id.phoneField)
        location = findViewById(R.id.locationField)

    }

}