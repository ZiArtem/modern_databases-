package com.example.modern_databases.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.modern_databases.R
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.modern_databases.adapters.CartActionListener
import com.example.modern_databases.adapters.PageActionListener
import com.example.modern_databases.adapters.PageAdapter
import com.example.modern_databases.adapters.SliderAdapter
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.data.entities.Cart
import com.example.modern_databases.data.entities.Favorite
import com.example.modern_databases.viewmodel.PrViewModel
import com.like.LikeButton
import com.like.OnLikeListener
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import java.util.*

class AdPageActivity : AppCompatActivity() {
    private lateinit var title: TextView
    private lateinit var descriptions: TextView
    private lateinit var price: TextView
    private lateinit var buy: Button
    private lateinit var fav: LikeButton
    private lateinit var slider: SliderView

    lateinit var mUserViewModel: PrViewModel
    private var id_ad: Int = 0
    private var save_user_id = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_page)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)
        val sharedPref: SharedPreferences =
            getSharedPreferences("user", Context.MODE_PRIVATE)
        save_user_id = sharedPref.getInt("id_user", -1)


        showAd()
//        likeButtonPush()

        var sliderItem: SliderAnimations
    }

    private fun showAd() {
        slider = findViewById(R.id.slider)

        val intent = intent
        id_ad = intent.getIntExtra("id_ad", 0)

        //Slider
        var adapter = SliderAdapter()
        slider.setSliderAdapter(adapter)
        mUserViewModel.getImageByIdAd(id_ad).observe(this, { im -> adapter.setData(im) })


        // Descriptions
        var adapter1 = PageAdapter(object : PageActionListener {
            override fun AdToCart(ad: AdDao.FullAd) {
                Thread(Runnable {
                    val cartItem = mUserViewModel.getCartByIdAd(ad.ad.id_ad)
                    if (cartItem.isEmpty()) {
                        mUserViewModel.addCartElement(Cart(0, 1, Date(), ad.ad.id_ad, save_user_id))
                    } else {
                        mUserViewModel.updateCartElement(
                            Cart(
                                cartItem[0].id_cart,
                                cartItem[0].num + 1,
                                cartItem[0].date,
                                cartItem[0].id_ad_,
                                cartItem[0].id_user_
                            )
                        )
                    }
                }).start()
            }

            override fun onFavoriteAdd(ad: AdDao.FullAd) {
                mUserViewModel.addFavorite(Favorite(0, ad.ad.id_ad, save_user_id))
            }

            override fun onFavoriteDelete(ad: AdDao.FullAd) {
                for (i in ad.fav)
                    if (i.id_ad_ == ad.ad.id_ad) {
                        mUserViewModel.deleteFavorite(i)
                        break
                    }
            }

            override fun BuyNow(ad: AdDao.FullAd) {
                TODO("Not yet implemented")
            }
        })

        val recyclerView: RecyclerView = findViewById(R.id.recycleviewTest2)
        recyclerView.adapter = adapter1
        recyclerView.layoutManager = LinearLayoutManager(this)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter1.setUserId(save_user_id)

        mUserViewModel.TestALlAdByIdAd(listOf(id_ad)).observe(this, { ad -> adapter1.setData(ad) })
    }

    private fun likeButtonPush() {
        fav.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {


                mUserViewModel.addFavorite(Favorite(0, id_ad, save_user_id))
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