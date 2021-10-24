package com.example.modern_databases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast

class RegistryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)
    }

    fun goMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun addUser(view: View){
        insertDataToDatabase()
    }

    private fun insertDataToDatabase() {
        var  firstName: EditText = findViewById(R.id.first_name)
        var  lastName: EditText = findViewById(R.id.Surname)
        var  password: EditText = findViewById(R.id.password)
        var  confirm: EditText = findViewById(R.id.confirm)
        var  login: EditText = findViewById(R.id.login)

        if (inputCheck(firstName.getText().toString(),lastName.getText().toString(),password.getText().toString(),confirm.getText().toString(),login.getText().toString())) {
            if (checkPasswordsConfim(password.getText().toString(),confirm.getText().toString())) {
                Toast.makeText(applicationContext,"Successful!!!!",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext,"Password mismatch",Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(applicationContext,"Please fill out all fields",Toast.LENGTH_SHORT).show()
        }

//        Toast.makeText(applicationContext,lastName.getText().toString(),Toast.LENGTH_LONG).show()
    }

    private fun checkPasswordsConfim (pass:String,conf:String):Boolean {
        return pass ==conf
    }

    private fun inputCheck (first:String,last:String,pass:String,conf:String,login:String):Boolean {
        return !( TextUtils.isEmpty(first) || TextUtils.isEmpty(last) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(conf) || TextUtils.isEmpty(login))
    }


}