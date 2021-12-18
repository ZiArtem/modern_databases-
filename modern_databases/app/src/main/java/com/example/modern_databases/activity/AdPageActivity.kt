package com.example.modern_databases.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.modern_databases.R
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.viewmodel.PrViewModel


class AdPageActivity : AppCompatActivity() {
    private lateinit var image:ImageView
    lateinit var mUserViewModel: PrViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_page)

        val intent = intent
        var id_ad = intent.getIntExtra("id_ad",0)
        image = findViewById(R.id.imageView)

        image.load("https://pbs.twimg.com/media/E00ZZrKXoAEiyla.jpg") {
            transformations(RoundedCornersTransformation(40f))
        }
        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get( PrViewModel::class.java)

        Thread(Runnable {
            var image1 =  mUserViewModel.getImageByIdAd(id_ad)
            runOnUiThread {
                image.load(image1[0].image) {
                    transformations(RoundedCornersTransformation(40f))
                }
            }
        }).start()


        Toast.makeText(applicationContext, id_ad.toString(), Toast.LENGTH_SHORT)
            .show()
    }
}