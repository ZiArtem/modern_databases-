package com.example.modern_databases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.modern_databases.data.UserViewModel

class MainActivity : AppCompatActivity() {
    lateinit var mUserViewModel: UserViewModel

    lateinit var  login: EditText
    lateinit var  password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        login = findViewById(R.id.login_main)
        password = findViewById(R.id.password_main)
    }

    fun goRegistery(view: View){
        val intent = Intent(this, RegistryActivity::class.java)
        startActivity(intent)
    }

    fun signIn (view: View){
        var login_s:String = login.getText().toString()
        var password_s:String = password.getText().toString()
        if (inputCheckSignIn(password_s,login_s)) {
                    // добавить работу с бд
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(applicationContext,"Fill in all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheckSignIn (pass:String,login:String):Boolean {
        return !( TextUtils.isEmpty(pass)  || TextUtils.isEmpty(login))
    }
}
