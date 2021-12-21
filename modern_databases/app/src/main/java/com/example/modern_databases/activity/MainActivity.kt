package com.example.modern_databases.activity


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.modern_databases.R
import com.example.modern_databases.viewmodel.PrViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var mUserViewModel: PrViewModel
    lateinit var login: EditText
    lateinit var password: EditText
    lateinit var goReg: TextView
    lateinit var next: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        checkSignIn()

        login = findViewById(R.id.login_main)
        password = findViewById(R.id.password_main)
        goReg = findViewById(R.id.createAccount)
        next = findViewById(R.id.buttonNext)

        goReg.setOnClickListener {
            goRegistery()
        }

        next.setOnClickListener {
            signIn()
        }
    }

    private fun goRegistery() {
        val intent = Intent(this, RegistryActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0);
    }

    private fun signIn() {
        var login_s: String = login.text.toString()
        var password_s: String = password.text.toString()

        if (inputCheckSignIn(password_s, login_s)) {
            Thread(Runnable {
                val user = mUserViewModel.getUser(login_s, password_s)
                if (user.isNotEmpty()) {
                    val sharedPref: SharedPreferences =
                        getSharedPreferences("user", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.apply {
                        putInt("id_user", user[0].id_user)
                        putBoolean("is_checked", true)
                    }.apply()
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("id_ad", user[0].id_user)
                    startActivity(intent)
                    finish();
                    overridePendingTransition(0, 0);
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Incorrect login or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }).start()
        } else {
            Toast.makeText(applicationContext, "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheckSignIn(pass: String, login: String): Boolean {
        return !(TextUtils.isEmpty(pass) || TextUtils.isEmpty(login))
    }

    private fun checkSignIn() {
        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val save_id = sharedPref.getInt("id_user", -1)
        val save_bool = sharedPref.getBoolean("is_checked", false)

        if (save_bool) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish();
            overridePendingTransition(0, 0);
        }
    }
}

