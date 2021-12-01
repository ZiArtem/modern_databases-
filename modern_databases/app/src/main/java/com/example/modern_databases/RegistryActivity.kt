package com.example.modern_databases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.modern_databases.data.User
import com.example.modern_databases.data.UserViewModel

class RegistryActivity : AppCompatActivity() {
    lateinit var mUserViewModel: UserViewModel

    lateinit var  firstName:EditText
    lateinit var  lastName: EditText
    lateinit var  password: EditText
    lateinit var  confirm: EditText
    lateinit var  login: EditText

    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)

        firstName =  findViewById(R.id.first_name)
        lastName = findViewById(R.id.Surname)
        password = findViewById(R.id.password)
        confirm = findViewById(R.id.confirm)
        login = findViewById(R.id.login)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

//        val adapter = ListAdapter(this)
//        mUserViewModel.readAllData.observe( this, Observer { user->adapter.setData(user) }
    }

    fun goMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun addUser(view: View){
        insertDataToDatabase()
    }

    private fun insertDataToDatabase() {
        var firstName_t:String = firstName.getText().toString()
        var lastName_t:String = lastName.getText().toString()
        var login_t:String = login.getText().toString()
        var password_t:String = password.getText().toString()
        var confirm_t:String =  confirm.getText().toString()

        if (inputCheck(firstName_t,lastName_t,password_t,confirm_t,login_t)) {
            if (checkPasswordsConfim(password_t,confirm_t)) {
                val user = User(0,firstName_t,lastName_t, login_t, password_t)
                mUserViewModel.addUser(user)
                Toast.makeText(applicationContext,"Successful!!!!",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Activity2::class.java)
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