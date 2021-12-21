package com.example.modern_databases.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.modern_databases.R
import com.example.modern_databases.viewmodel.PrViewModel
import com.example.modern_databases.data.data_class.User
import com.example.modern_databases.data.data_class.UserInformation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class RegistryActivity : AppCompatActivity() {
    lateinit var mUserViewModel: PrViewModel
    lateinit var firstName: EditText
    lateinit var lastName: EditText
    lateinit var password: EditText
    lateinit var confirm: EditText
    lateinit var login: EditText
    lateinit var cross: TextView
    lateinit var registryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)

        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.lastName)
        password = findViewById(R.id.password_registry)
        confirm = findViewById(R.id.confirm)
        login = findViewById(R.id.login_registry)
        registryButton = findViewById(R.id.end_registry)
        cross = findViewById(R.id.cross)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        registryButton.setOnClickListener {
            insertDataToDatabase()
        }

        cross.setOnClickListener {
            goMain()
        }
    }

    private fun goMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0);
    }

    private fun insertDataToDatabase() {
        var firstName_t: String = firstName.text.toString()
        var lastName_t: String = lastName.text.toString()
        var login_t: String = login.text.toString()
        var password_t: String = password.text.toString()
        var confirm_t: String = confirm.text.toString()

        if (inputCheck(firstName_t, lastName_t, password_t, confirm_t, login_t)) {
            if (password_t == confirm_t) {
                Thread(Runnable {
                    val user_same_login = mUserViewModel.checkUniqueLogin(login_t)
                    if (user_same_login.isEmpty()) {
                        val user = User(0, firstName_t, lastName_t, login_t, password_t)
                        mUserViewModel.addUser(user)
                        lifecycleScope.launch {
                            delay(500)
                            Thread(Runnable {
                                var id_user = mUserViewModel.getUserIdByLogin(login_t)
                                addUserInfo(id_user[0])
                            }).start()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "this login already exists",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }).start()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Password and confirm mismatch",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(applicationContext, "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun addUserInfo(id_user: Int) {
        val sharedPref: SharedPreferences =
            getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putInt("id_user", id_user)
            putBoolean("is_checked", true)
        }.apply()

        lifecycleScope.launch {
            mUserViewModel.addUserInfo(
                UserInformation(
                    0,
                    " ",
                    " ",
                    Date(),
                    " ",
                    getBitmap("https://ebar.co.za/wp-content/uploads/2018/01/menu-pattern-1-1.png"),
                    id_user
                )
            )
        }
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish();
    }

    private fun inputCheck(
        first: String,
        last: String,
        pass: String,
        conf: String,
        login: String
    ): Boolean {
        return !(TextUtils.isEmpty(first) || TextUtils.isEmpty(last) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(
            conf
        ) || TextUtils.isEmpty(login))
    }

    private suspend fun getBitmap(path: String): Bitmap {
        val loading: ImageLoader = ImageLoader(this)
        val request = ImageRequest.Builder(this).data(path).build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}