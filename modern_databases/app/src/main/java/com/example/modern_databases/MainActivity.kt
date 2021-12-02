package com.example.modern_databases


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.modern_databases.data.UserViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var mUserViewModel: UserViewModel

    lateinit var  login: EditText
    lateinit var  password: EditText
    lateinit var  goReg: TextView
    lateinit var  next: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        login = findViewById(R.id.login_main)
        password = findViewById(R.id.password_main)
        goReg = findViewById(R.id.createAccount)
        next = findViewById(R.id.buttonNext)

        goReg.setOnClickListener{
            lifecycleScope.launch {
                goRegistery()}
        }

        next.setOnClickListener{
            lifecycleScope.launch {
                signIn()}
        }

    }

   private fun goRegistery(){
        val intent = Intent(this, RegistryActivity::class.java)
        startActivity(intent)
    }

   private fun signIn (){
        var login_s:String = login.getText().toString()
        var password_s:String = password.getText().toString()

        if (inputCheckSignIn(password_s,login_s)) {
            Thread(Runnable {
                val user = mUserViewModel.getUser(login_s,password_s)
                if (!user.isEmpty()) {
                    val intent = Intent(this, Activity2::class.java)
                    startActivity(intent)
                    finish();
                } else {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Incorrect login or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }).start()
        } else {
            Toast.makeText(applicationContext,"Please fill out all fields",Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheckSignIn (pass:String,login:String):Boolean {
        return !( TextUtils.isEmpty(pass)  || TextUtils.isEmpty(login))
    }
}


