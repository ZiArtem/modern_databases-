package com.example.modern_databases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Activity3 : AppCompatActivity() {
    lateinit var  ref: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        ref=  findViewById(R.id.ref_button)
        ref.setOnClickListener {  GoToExempleLayout()  }
    }

    private fun GoToExempleLayout () {
        val intent = Intent(this, Activity2::class.java)
        startActivity(intent)
    }
}