package com.example.modern_databases.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.modern_databases.R
import com.example.modern_databases.data.data_class.Ad
import com.example.modern_databases.data.data_class.Image
import com.example.modern_databases.viewmodel.PrViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.util.*

class TestActivity : AppCompatActivity() {
    lateinit var mUserViewModel: PrViewModel
    lateinit var test: Button
//    lateinit var addImage_: Button
//    lateinit var test4: Button
//    lateinit var test5: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get( PrViewModel::class.java)

        test = findViewById(R.id.test_button)
//        addImage_ = findViewById(R.id.button_image2)
//        test4 = findViewById(R.id.buttontest4)
//        test5 = findViewById(R.id.button_test_5)

        test.setOnClickListener { addAdTestFunction() }
//        addImage_.setOnClickListener { addImageTest() }
//        test4.setOnClickListener { addFavorite() }
//        test5.setOnClickListener { addUserInfo() }
        navigationBar()
    }

    private fun navigationBar() {
        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.test)
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
                R.id.settings -> {
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

//    private fun addUserInfo() {
//        lifecycleScope.launch {
//            var userInformation: UserInformation = UserInformation(
//                0,
//                "8_________6",
//                "z________@yandex.ru",
//                Date(),
//                "Nizhny Novgorod",
//                getBitmap("https://pbs.twimg.com/media/E00ZZrKXoAEiyla.jpg"),
//                1
//            )
//            mUserViewModel.addUserInfo(userInformation)
//        }
//    }

//    private fun addFavorite() {
//        var fav: Favorite = Favorite(0, 3, 1)
//        mUserViewModel.addFavorite(fav)
//    }

    private fun addAdTestFunction() {
//        lifecycleScope.launch {
//            var userInformation: UserInformation = UserInformation(
//                1,
//                "8_________6",
//                "z________@yandex.ru",
//                Date(),
//                "Nizhny Novgorod",
//                getBitmap("https://pbs.twimg.com/media/E00ZZrKXoAEiyla.jpg"),
//                1
//            )
//            mUserViewModel.updateUserInfo(userInformation)
//        }

        var images_list: ArrayList<String> = ArrayList()

        for (i in 1..20)
            images_list.add("https://pbs.twimg.com/media/E00ZZrKXoAEiyla.jpg")
        images_list.add("https://fun-cats.ru/wp-content/uploads/3/0/7/3071381c7efe0c6c94cec8f4eb4442e6.jpeg")
        images_list.add("https://pbs.twimg.com/media/EkIUGBXWoAE9b0i.jpg")
        images_list.add("https://mirsobaki.ru/wp-content/uploads/2019/01/Siba-inu-88.jpg")
        images_list.add("https://doggav.ru/wp-content/uploads/siba-inu_24.jpg")
        images_list.add("https://kot-pes.com/wp-content/uploads/2019/06/post_5cf6ff9b6710e-765x754.jpg")
        images_list.add("https://porodisobak.ru/wp-content/uploads/2021/07/siba-inu-12.jpg")
        images_list.add("https://lapkins.ru/upload/iblock/945/945b1c8bba53680aca6d63dcd04fce9b.jpg")
        images_list.add("https://hypecrib.com/wp-content/uploads/2019/12/8-1.jpg")
        images_list.add("https://koshka-pushinka.ru/wp-content/uploads/2019/05/Hatchi-13.jpg")
        images_list.add("https://koshka-pushinka.ru/wp-content/uploads/2019/05/Hatchi-13.jpg")
        images_list.add("https://beopeo.com/wp-content/uploads/2021/05/1558264640_issyrider_56328817_129932821482783_8321331283586384766_n-768x698.jpg")

        var ad: Ad = Ad(
            0,
            "Test title  " + (0..100).random(),
            "test",
            (0..15).random(),
            (0..10000).random(),
            Date(),
            1
        )
        mUserViewModel.addAd(ad)

        lifecycleScope.launch {
            val im = Image(
                0,
                getBitmap(images_list[(1..29).random()]),
                1,
                1
            )
            mUserViewModel.addImage(im)
        }

        for (item: Int in 1..40) {
            ad = Ad(
                0,
                "Test title  " + (0..100).random(),
                "test",
                (0..15).random(),
                (0..10000).random(),
                Date(),
                1
            )
            mUserViewModel.addAd(ad)

            lifecycleScope.launch {
                val im = Image(
                    0,
                    getBitmap(images_list[(1..29).random()]),
                    item + 1,
                    1
                )
                mUserViewModel.addImage(im)
            }
        }
    }

    private suspend fun getBitmap(path: String): Bitmap {
        val loading: ImageLoader = ImageLoader(this)
        val request = ImageRequest.Builder(this).data(path).build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

//    private fun addImageTest() {
//        lifecycleScope.launch {
//            val im = Image(
//                0,
//                getBitmap("https://beopeo.com/wp-content/uploads/2021/05/1558264640_issyrider_56328817_129932821482783_8321331283586384766_n-768x698.jpg"),
//                3,
//                1
//            )
//            mUserViewModel.addImage(im)
//        }
//        Toast.makeText(applicationContext, "image added successfully", Toast.LENGTH_SHORT).show()
//    }

//    private fun deleteAll() {
//        Thread(Runnable {
//            val ad = mUserViewModel.getAllAd()
//            for (i in 0..ad.size - 1) {
//                mUserViewModel.deleteAd(ad[i])
//            }
//        }).start()
//    }
}