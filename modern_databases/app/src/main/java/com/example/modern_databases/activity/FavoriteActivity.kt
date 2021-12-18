package com.example.modern_databases.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_databases.*
import com.example.modern_databases.adapters.AdActionListener1
import com.example.modern_databases.adapters.FavoriteAdAdapter
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.data.data_class.Ad
import com.example.modern_databases.data.data_class.Favorite
import com.example.modern_databases.viewmodel.PrViewModel

import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoriteActivity : AppCompatActivity() {
    lateinit var mUserViewModel: PrViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        showFavoriteAd()

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.favorite)
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
                R.id.orders -> {
                    val intent = Intent(this, OrdersActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
                R.id.settings -> {
                    val intent = Intent(this, SettigActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
                R.id.test -> {
                    val intent = Intent(this, TestActivity::class.java)
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
            override fun onAdDeteils(ad: AdDao.FullAd) {
                val intent = Intent(this@FavoriteActivity, AdPageActivity::class.java)
                intent.putExtra("id_ad", ad.ad.id_ad.toInt())

                startActivity(intent)
                overridePendingTransition(0, 0)
            }

            override fun onFavoriteAdd(ad: AdDao.FullAd) {
                mUserViewModel.addFavorite(Favorite(0, ad.ad.id_ad, 1))
            }

            override fun buy(ad: AdDao.FullAd) {
                Toast.makeText(
                    applicationContext,
                    "this feature hasn't been implemented yet",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFavoriteDelete(ad: AdDao.FullAd) {
                mUserViewModel.deleteFavorite(ad.fav[0])
            }
        })
        recyclerView = findViewById(R.id.recycleviewFavorite)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        Thread(Runnable {
            var fav: List<Int> = mUserViewModel.getAllFavoriteAd(1)
            runOnUiThread {
                mUserViewModel.TestALlAdByIdAd(fav)
                    .observe(this, Observer { ad -> adapter.setData(ad) })
            }
//            }
//            var d1: LiveData<List<Ad>> = mUserViewModel.getAdByListIdAd(fav)
//            runOnUiThread {
//                d1.observe(this, Observer { ad -> adapter.setData(ad) })
//                mUserViewModel.getAllPreviewImage(fav).observe(this, Observer { image -> adapter.setImage(image) })
//            }
            }).start()
        }
    }