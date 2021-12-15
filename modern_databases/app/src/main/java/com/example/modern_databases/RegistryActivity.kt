package com.example.modern_databases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
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
    lateinit var  registryButton: Button
    lateinit var  cross: TextView

    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)

        firstName =  findViewById(R.id.first_name)
        lastName = findViewById(R.id.lastName)
        password = findViewById(R.id.password_registry)
        confirm = findViewById(R.id.confirm)
        login = findViewById(R.id.login_registry)

        registryButton = findViewById(R.id.end_registry)
        cross = findViewById(R.id.cross)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        registryButton.setOnClickListener {
            insertDataToDatabase()
        }

        cross.setOnClickListener {
            goMain()
        }
    }

    private fun goMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private  fun insertDataToDatabase() {
        var firstName_t:String = firstName.getText().toString()
        var lastName_t:String = lastName.getText().toString()
        var login_t:String = login.getText().toString()
        var password_t:String = password.getText().toString()
        var confirm_t:String =  confirm.getText().toString()

        if (inputCheck(firstName_t,lastName_t,password_t,confirm_t,login_t)) {
            if (checkPasswordsConfirm(password_t,confirm_t)) {
                Thread(Runnable {
                    val user_same_login = mUserViewModel.checkUniqueLogin(login_t)
                    if (user_same_login.isEmpty()) {
                        val user = User(0,firstName_t,lastName_t, login_t, password_t)
                        mUserViewModel.addUser(user)
                        runOnUiThread {
                            Toast.makeText(applicationContext,"Successful!!!!",Toast.LENGTH_SHORT).show()
                        }
                        val intent = Intent(this, Activity3::class.java)
                        startActivity(intent)
                        finish();
                    }else {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "this login already exists", Toast.LENGTH_SHORT).show()
                        }
                    }
                }).start()
            } else {
                Toast.makeText(applicationContext,"Password and confirm mismatch",Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(applicationContext,"Please fill out all fields",Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPasswordsConfirm (pass:String,conf:String):Boolean {
        return pass ==conf
    }

    private fun inputCheck (first:String,last:String,pass:String,conf:String,login:String):Boolean {
        return !( TextUtils.isEmpty(first) || TextUtils.isEmpty(last) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(conf) || TextUtils.isEmpty(login))
    }
}