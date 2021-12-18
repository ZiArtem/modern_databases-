package com.example.modern_databases.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.modern_databases.R
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.denzcoskun.imageslider.ImageSlider
import com.example.modern_databases.data.data_class.Favorite
import com.example.modern_databases.viewmodel.PrViewModel
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.ad_item_1.*

class AdPageActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var imageSlider:ImageSlider
    private lateinit var title: TextView
    private lateinit var descriptions: TextView
    private lateinit var price: TextView
    private lateinit var date: TextView
    private lateinit var buy: Button
    private lateinit var fav: LikeButton

    lateinit var mUserViewModel: PrViewModel
    var id_ad: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_page)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)
        showAd()
        likeButtonPush()
    }

    private fun showAd() {
        image = findViewById(R.id.imageView)


        descriptions = findViewById(R.id.description_1)
        price = findViewById(R.id.price_1)
        date = findViewById(R.id.data_1)
        title = findViewById(R.id.title_1)
        fav = findViewById(R.id.like_button_1)
        buy = findViewById(R.id.buttonBuy)
//        imageSlider = findViewById(R.Id)

//        image.load("https://ebar.co.za/wp-content/uploads/2018/01/menu-pattern-1-1.png") {
//            transformations(RoundedCornersTransformation(40f))
//        }

        val intent = intent
        id_ad = intent.getIntExtra("id_ad", 0)
        Thread(Runnable {
            var image1 = mUserViewModel.getImageByIdAd(id_ad)
            var ad = mUserViewModel.getAdById(id_ad)
            var fav_ = mUserViewModel.getFavoriteByIdAd(id_ad)
            runOnUiThread {
                if (!image1.isEmpty()) {
                    image.load(image1[0].image) {
                        transformations(RoundedCornersTransformation(40f))
                    }
                }
                title.setText(ad[0].title.toString())
                price.setText(ad[0].price.toString() + " Руб.")
                date.setText(ad[0].date.toString())
                descriptions.setText(ad[0].description.toString())
                if (fav_.isEmpty()) {
                    fav.setLiked(false)
                } else {
                    fav.setLiked(true)
                }
            }
        }).start()
    }

    private fun likeButtonPush() {
        fav.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                mUserViewModel.addFavorite(Favorite(0, id_ad, 1))
            }

            override fun unLiked(likeButton: LikeButton) {
                Thread(Runnable {
                    var favorite1 = mUserViewModel.getFavoriteByIdAd(id_ad)
                    mUserViewModel.deleteFavorite(favorite1[0])
                }).start()
            }
        })
    }
}