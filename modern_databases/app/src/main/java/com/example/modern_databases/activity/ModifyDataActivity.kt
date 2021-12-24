package com.example.modern_databases.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.modern_databases.R
import com.example.modern_databases.data.entities.UserInformation
import com.example.modern_databases.viewmodel.PrViewModel
import org.w3c.dom.Text

class ModifyDataActivity : AppCompatActivity() {
    private var id_user = -1
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var email: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var location: TextView
    private lateinit var saveInformation: Button
    private lateinit var mUserViewModel: PrViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_data)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        val intent = intent
        id_user = intent.getIntExtra("id_user", -1)

        firstName = findViewById(R.id.firstNameModify)
        lastName = findViewById(R.id.lastNameModify)
        email = findViewById(R.id.emailFieldModify)
        phoneNumber = findViewById(R.id.phoneFieldModify)
        location = findViewById(R.id.locationFieldModify)
        saveInformation = findViewById(R.id.saveInformationModify)

        Thread(Runnable {
            val user = mUserViewModel.getUserInfo(id_user)
            val userInf = mUserViewModel.getUserInformation(id_user)

            firstName.text = user[0].firstName
            lastName.text = user[0].lastName
            email.text = userInf[0].email
            phoneNumber.text = userInf[0].phone_number
            location.text = userInf[0].location
        }).start()
        saveInformation.setOnClickListener { pushSaveInformation(id_user) }
    }

    private fun pushSaveInformation(userId: Int) {
        if (firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && email.text.isNotEmpty() && phoneNumber.text.isNotEmpty() && location.text.isNotEmpty()) {
            Thread(Runnable {
                val userInf = mUserViewModel.getUserInformation(id_user)
                mUserViewModel.updateUserInfo(
                    UserInformation(
                        userInf[0].id_info,
                        phoneNumber.text.toString(),
                        email.text.toString(),
                        userInf[0].date_registry,
                        location.text.toString(),
                        userInf[0].image_user,
                        userInf[0].id_user_
                    )
                )
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        "Data has been successfully changed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                val intent = Intent(this, SettigActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0);
                finish()
            }).start()

        } else {
            Toast.makeText(applicationContext, "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }
}