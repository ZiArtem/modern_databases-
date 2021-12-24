package com.example.modern_databases.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.R
import com.example.modern_databases.data.entities.Ad
import com.example.modern_databases.data.entities.Image
import com.example.modern_databases.viewmodel.PrViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
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
    private lateinit var changePassword: Button
    private lateinit var modifyInformation: Button
//    private lateinit var test: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settig)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        setInformation()
        bottomNavigation()

        changePassword = findViewById(R.id.changePassword)
//        test = findViewById(R.id.test_button)
        signOut = findViewById(R.id.exit)
        modifyInformation = findViewById(R.id.modifyData)

        signOut.setOnClickListener { signOut() }
        changePassword.setOnClickListener { ChangePassword() }
//        test.setOnClickListener { addAdTestFunction() }
        modifyInformation.setOnClickListener { modifyInformationFun() }
    }

    private fun modifyInformationFun() {
        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val save_id = sharedPref.getInt("id_user", -1)

        val intent = Intent(this, ModifyDataActivity::class.java)
        intent.putExtra("id_user", save_id)
        startActivity(intent)
        overridePendingTransition(0, 0);
    }

    private fun ChangePassword() {
        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val save_id = sharedPref.getInt("id_user", -1)

        val intent = Intent(this, ChangePasswordActivity::class.java)
        intent.putExtra("id_user", save_id)
        startActivity(intent)
        overridePendingTransition(0, 0);
    }

    private fun bottomNavigation() {
        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.settings
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
                R.id.order -> {
                    val intent = Intent(this, New::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
            }
            true
        }
    }

    private fun setInformation() {

        lastName = findViewById(R.id.userName)
        secondName = findViewById(R.id.userLastName)
        userImage = findViewById(R.id.userImage)
        email = findViewById(R.id.email)
        phoneNumber = findViewById(R.id.phoneNumber)
        location = findViewById(R.id.location)
        registrationDate = findViewById(R.id.date_registry)

        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val save_id = sharedPref.getInt("id_user", -1)

        Thread(Runnable {
            var user = mUserViewModel.getUserInfo(save_id)
            var userInfo = mUserViewModel.getUserInformation(save_id)
            runOnUiThread {
                lastName.text = user[0].firstName
                secondName.text = user[0].lastName
                email.text = userInfo[0].email
                location.text = userInfo[0].location
                val sdf = SimpleDateFormat("dd.MM HH:mm")
                registrationDate.text = sdf.format(userInfo[0].date_registry).toString()
                phoneNumber.text = userInfo[0].phone_number
                userImage.load(userInfo[0].image_user) {
                    transformations(RoundedCornersTransformation(40f))
                }
            }
        }).start()
    }

    private fun signOut() {
        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putInt("id_user", -1)
            putBoolean("is_checked", false)
        }.apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0);
        finish();
    }

    private fun addAdTestFunction() {
        lifecycleScope.launch {
            var ad: Ad
            var im: Image
//            mUserViewModel.addImage(Image(
//                0,
//                getBitmap(""),
//                5,
//                1
//            ))
        }
    }

    private suspend fun getBitmap(path: String): Bitmap {
        val loading: ImageLoader = ImageLoader(this)
        val request = ImageRequest.Builder(this).data(path).build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}