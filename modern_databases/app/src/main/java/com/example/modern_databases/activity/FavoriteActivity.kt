package com.example.modern_databases.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_databases.*
import com.example.modern_databases.adapters.AdActionListener1
import com.example.modern_databases.adapters.FavoriteAdAdapter
import com.example.modern_databases.data.dao.FavoriteDao
import com.example.modern_databases.data.entities.Cart
import com.example.modern_databases.viewmodel.PrViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class FavoriteActivity : AppCompatActivity() {
    lateinit var mUserViewModel: PrViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        showFavoriteAd()
        bottomNavigation()
    }

    private fun bottomNavigation() {
        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.favorite
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
//                R.id.favorite-> {
//                    val intent = Intent(this, FavoriteActivity::class.java)
//                    startActivity(intent)
//                }
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
                R.id.order -> {
                    val intent = Intent(this, New::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
            }
            true
        }
    }

    private fun showFavoriteAd() {
        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        adapter = FavoriteAdAdapter(object : AdActionListener1 {
            override fun onAdDeteils(fav: FavoriteDao.FavoriteAndAdAndImage) {
                val intent = Intent(this@FavoriteActivity, AdPageActivity::class.java)
                intent.putExtra("id_ad", fav.adList[0].ad.id_ad)

                startActivity(intent)
                overridePendingTransition(0, 0)
            }

            override fun addToCart(fav: FavoriteDao.FavoriteAndAdAndImage) {
                Thread(Runnable {
                    val cartItem = mUserViewModel.getCartByIdAd(fav.favorite.id_ad_)
                    if (cartItem.isEmpty()) {
                        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
                        val save_id_user = sharedPref.getInt("id_user", -1)
                        mUserViewModel.addCartElement(Cart(0, 1, Date(), fav.favorite.id_ad_, save_id_user))
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

            override fun onFavoriteDelete(fav: FavoriteDao.FavoriteAndAdAndImage) {
                mUserViewModel.deleteFavorite(fav.favorite)
            }
        })
        recyclerView = findViewById(R.id.recycleviewFavorite)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val save_id_user = sharedPref.getInt("id_user", -1)
        mUserViewModel.getAllFavoriteByUser(save_id_user)
            .observe(this, Observer { fav -> adapter.setData(fav) })

    }
}