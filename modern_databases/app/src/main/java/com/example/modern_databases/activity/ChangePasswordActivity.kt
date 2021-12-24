package com.example.modern_databases.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.modern_databases.R
import com.example.modern_databases.data.entities.User
import com.example.modern_databases.viewmodel.PrViewModel

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var oldPassword: TextView
    private lateinit var newPassword: TextView
    private lateinit var confirmPassword: TextView
    private lateinit var updatePassword: Button
    private var id_ad = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        oldPassword = findViewById(R.id.oldPassword)
        newPassword = findViewById(R.id.passwordUpdate)
        confirmPassword = findViewById(R.id.confirmUpdate)
        updatePassword = findViewById(R.id.saveNewPassword)

        val intent = intent
        id_ad = intent.getIntExtra("id_user", -1)

        updatePassword.setOnClickListener { UpdatePassword() }
    }

    private fun UpdatePassword() {
        val mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        if (oldPassword.text.isNotEmpty() && newPassword.text.isNotEmpty() && confirmPassword.text.isNotEmpty()) {
            Thread(Runnable {
                val password =
                    mUserViewModel.checkPasswordByUser(oldPassword.text.toString(), id_ad)
                if (password.isNotEmpty()) {
                    if (newPassword.text.toString() == confirmPassword.text.toString()) {
                        mUserViewModel.updateUser(
                            User(
                                password[0].id_user,
                                password[0].firstName,
                                password[0].lastName,
                                password[0].login,
                                newPassword.text.toString()
                            )
                        )
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Password changed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        val intent = Intent(this, SettigActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0);
                        finish()
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "New password and confirm mismatch",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                } else {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Incorrect password",
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
}