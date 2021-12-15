package com.example.modern_databases
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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.Image
import com.example.modern_databases.data.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Activity2 : AppCompatActivity() {
    lateinit var mUserViewModel: UserViewModel
    lateinit var  test: Button
    lateinit var  delete: Button
    lateinit var  test2: Button
    lateinit var  addImage_: Button
    lateinit var  changeImage: Button
    lateinit var  image: ImageView
    lateinit var  cross: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        test=  findViewById(R.id.test_button)
//        test2=  findViewById(R.id.test_button2)
//        image = findViewById(R.id.imageView)
        addImage_ = findViewById(R.id.button_image2)
        changeImage = findViewById(R.id.button_image)
        cross = findViewById(R.id.cross1)
        delete= findViewById(R.id.delete)

        test.setOnClickListener {  addAdTestFunction () }
//        test2.setOnClickListener { getAdTest () }
        addImage_.setOnClickListener { addImageTest ()  }
        changeImage.setOnClickListener {  changeImage()  }
        cross.setOnClickListener {  goSignIn()  }
        delete.setOnClickListener {  deleteAll()  }

//        image.load("https://www.dimm.com.uy/productos/imgs/playstation-5-regular-pre-venta-66105-34.jpg") {
//            transformations(RoundedCornersTransformation(40f))
//        }

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.test)
        bottomNavigationView.setOnNavigationItemSelectedListener { item->
            when(item.itemId) {
                R.id.home-> {
                    val intent = Intent(this, Activity3::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
                R.id.favorite-> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
                R.id.orders-> {
                    val intent = Intent(this, OrdersActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
                R.id.settings-> {
                    val intent = Intent(this, SettigActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
//                R.id.test-> {
//                    val intent = Intent(this, Activity2::class.java)
//                    startActivity(intent)
//                }
            }
            true
        }
    }

    private fun addAdTestFunction () {
        var ad: Ad = Ad(0, "ony","test", 2,0,Date(),1)
        mUserViewModel.addAd(ad)
//        Toast.makeText(applicationContext,"ad added successfully!!!!", Toast.LENGTH_SHORT).show()
    }

    private fun getAdTest () {
        Thread(Runnable {

//            val ad = mUserViewModel.getByKeyword("3090")
            val ad = mUserViewModel.getAdById(1)
            if (!ad.isEmpty()) {
                runOnUiThread {
                    val sdf = SimpleDateFormat("dd MM,yyy -HH:mm")

                    Toast.makeText(
                        applicationContext,
                        ("test 1 ad \n" +
                                " title = " + ad[0].title + "\n description = " + ad[0].description + " \n" +
                                " price = " + ad[0].price + " \n" + "date of placement " +sdf.format(ad[0].date)),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        "There are no ads in the database",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }).start()
    }

    private suspend fun getBitmap(path:String): Bitmap {
        val loading:ImageLoader = ImageLoader(this)
        val request = ImageRequest.Builder(this).data (path).build()

        val result =(loading.execute(request)as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private  fun addImageTest () {
        lifecycleScope.launch {
            val im = Image(
                0,
                getBitmap("https://vplate.ru/images/article/orig/2019/04/siba-inu-opisanie-porody-harakter-i-soderzhanie.jpg"),1,1
            )
            mUserViewModel.addImage(im)
        }
        Toast.makeText(applicationContext, "image added successfully", Toast.LENGTH_SHORT).show()
    }

    private  fun changeImage () {
        Thread(Runnable {
            var im1 =  mUserViewModel.getImageById(1)
            image.load(im1[0].image) {
                crossfade(600)
                transformations(RoundedCornersTransformation(40f))
            }
        }).start()
    }

    private fun goSignIn() {
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

    private fun deleteAll() {
        Thread(Runnable {
            val ad = mUserViewModel.getAllAd()
            for (i in 0..ad.size-1) {
                mUserViewModel.deleteAd(ad[i])
            }
        }).start()
    }

}