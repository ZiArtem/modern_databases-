package com.example.modern_databases.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.R
import com.example.modern_databases.viewmodel.PrViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat

class SettigActivity : AppCompatActivity() {
    private lateinit var mUserViewModel: PrViewModel
    private lateinit var lastName: TextView
    private lateinit var secondName: TextView
    private lateinit var signOut: TextView
    private lateinit var userImage: ImageView
    private lateinit var email: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var location: TextView
    private lateinit var registrationDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settig)
//        setinformation()

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.settings)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
                R.id.favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
                R.id.cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
//                R.id.settings-> {
//                    val intent = Intent(this, SettigActivity::class.java)
//                    startActivity(intent)
//                }
                R.id.test -> {
                    val intent = Intent(this, TestActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
            }
            true
        }
        signOut = findViewById(R.id.exit)
        signOut.setOnClickListener { signOut() }
    }

    private fun setinformation() {
        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get( PrViewModel::class.java)
        lastName = findViewById(R.id.userName)
        secondName = findViewById(R.id.userLastName)
        userImage = findViewById(R.id.userImage)
        email = findViewById(R.id.email)
        phoneNumber = findViewById(R.id.phoneNumber)
        location = findViewById(R.id.location)
        registrationDate = findViewById(R.id.date_registry)
//        userImage.load("https://pbs.twimg.com/media/E00ZZrKXoAEiyla.jpg") {
//            transformations(RoundedCornersTransformation(40f))
//        }

        Thread(Runnable {
            var user = mUserViewModel.getUserInfo(1)
            var userInfo = mUserViewModel.getUserInformation(1)
            runOnUiThread {
                lastName.setText(user[0].firstName)
                secondName.setText(user[0].lastName)
                email.setText(userInfo[0].email)
                location.setText(userInfo[0].location)
                val sdf = SimpleDateFormat("dd.MM HH:mm")
                registrationDate.setText(sdf.format(userInfo[0].date_registry).toString())
                phoneNumber.setText(userInfo[0].phone_number)
                userImage.load(userInfo[0].image_user) {
                    transformations(RoundedCornersTransformation(40f))
                }
            }
        }).start()
    }

    private fun signOut() {
        val sharedPref: SharedPreferences = getSharedPreferences("passw", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putInt("id_user", -1)
            putBoolean("is_checked", false)
        }.apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish();
    }
}