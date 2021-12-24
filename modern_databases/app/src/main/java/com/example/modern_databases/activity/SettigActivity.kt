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
import java.util.*

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
    private lateinit var test: Button

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
        test = findViewById(R.id.test_button)
        signOut = findViewById(R.id.exit)
        modifyInformation = findViewById(R.id.modifyData)

        signOut.setOnClickListener { signOut() }
        changePassword.setOnClickListener { ChangePassword() }
        test.setOnClickListener { addAdTestFunction() }
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



            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-57.userapi.com/impg/-yuWsYF4Wm0RaQHuSXnKGq2wu-mue1Sb2_oEIw/dz3G7JBqms8.jpg?size=1061x709&quality=96&sign=b1e897287abcb8f673e3257aeb7ef717&type=album"),
                6,
                1
            ))

            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-60.userapi.com/impg/Sd-ANIDvs7y76cimh_sAs1Xuvh3WV_x_EWXkkQ/DCW5MUz8piY.jpg?size=1020x721&quality=96&sign=9b1f360ffacc2462a81b8ad71dda5544&type=album"),
                6,
                2
            ))

            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-19.userapi.com/impg/J3jhyU89KIpWc9zO5Svo1kvKQYVS8INaoQhkdw/zjsMN_BjHgw.jpg?size=585x769&quality=96&sign=693b2e03a5d5427b27e10a557960c464&type=album"),
                7,
                1
            ))

            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-81.userapi.com/impg/kZcW1mP_zfA9CNlvDA-tU6uEkLFKvtcJAqZSdQ/RDHi35c4aJw.jpg?size=662x750&quality=96&sign=1d4287cb0d37be9820f3fce7c582f3f2&type=album"),
                7,
                2
            ))

            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-79.userapi.com/impg/JF6nZSrt2l9IhfgTxyCi2HU7GDvfhFO0AhERWQ/jw3-hJoVPjg.jpg?size=1095x707&quality=96&sign=646ccaffdf4f14d46405975b0294efcb&type=album"),
                8,
                1
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-57.userapi.com/impg/77t9v6BvcT8CvlJvac56jHV0I25AMZ5XwP9egA/kmAGJJYwtis.jpg?size=891x695&quality=96&sign=d7191c6bb3a0bacc7d2ba7e7480f4397&type=album"),
                8,
                2
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-64.userapi.com/impg/0Upt4oKNArO5QiDd_czZ0cFmrnK48kPs_qGZng/d54NegWyW2M.jpg?size=774x702&quality=96&sign=9008fe3352182c0187ffa2355213c5b1&type=album"),
                8,
                3
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-31.userapi.com/impg/Gg7hqtX4AJqzq0xYbTJBcvO640_LUOakyodnLw/wXaM5LZiCKM.jpg?size=1109x522&quality=96&sign=50e05c5b17b537a9143e61d259ce58e7&type=album"),
                9,
                1
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-73.userapi.com/impg/I6tAIMWIWAuAbFVdbCU3Pi8xN5Nx3nH9nItN5g/COVPv5W4suA.jpg?size=904x683&quality=96&sign=378db34fe537b2b9cafc42eb0dc4867a&type=album"),
                9,
                2
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-9.userapi.com/impg/N9fSvg3jNRzLq5PJo4JfOSIvXDoyoF-RqmnKQw/SaOeiIgJc0w.jpg?size=1123x706&quality=96&sign=728bf92b8572f473376aa7965df0466d&type=album"),
                9,
                3
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-80.userapi.com/impg/iujk6CyF34xG2huySKP9TIWKJXx4RiJSgkoYwA/w8liUSoEEtM.jpg?size=703x709&quality=96&sign=357df4a136fae8f05f53f2c23cbfc157&type=album"),
                10,
                1
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-88.userapi.com/impg/fAczV3DWCYzekWGJ3OCN5XFNie8RhPI2VPziSQ/4VBKVAPCTrM.jpg?size=606x706&quality=96&sign=ecde21d96b1e355a70e127868530a6e9&type=album"),
                10,
                2
            ))
            mUserViewModel.addImage(Image(
                0,
                getBitmap("https://sun9-43.userapi.com/impg/26JPN-HuA8KQEMQJnmKituCtLLXIjvnmXJEIbA/x5FTW1Av9NQ.jpg?size=722x696&quality=96&sign=fa37586a6baaf9eae1020dac49622bad&type=album"),
                10,
                3
            ))
        }
    }

    private suspend fun getBitmap(path: String): Bitmap {
        val loading: ImageLoader = ImageLoader(this)
        val request = ImageRequest.Builder(this).data(path).build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

}