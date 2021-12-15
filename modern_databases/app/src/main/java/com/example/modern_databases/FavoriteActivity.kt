package com.example.modern_databases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.Favorite
import com.example.modern_databases.data.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoriteActivity : AppCompatActivity() {
    lateinit var mUserViewModel: UserViewModel
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
                    val intent = Intent(this, Activity3::class.java)
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
                    val intent = Intent(this, Activity2::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
            }
            true
        }
    }

    private fun showFavoriteAd () {
        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        adapter = FavoriteAdAdapter()
        recyclerView = findViewById(R.id.recycleviewFavorite)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        mUserViewModel.getAllImage.observe(this, Observer { image -> adapter.setImage(image) })
        Thread(Runnable {
        var fav: List<Favorite> =  mUserViewModel.getAllFavoriteByUser(1)
        var fav_ad: ArrayList<Int> = ArrayList()
        for (item in fav)
            fav_ad.add(item.id_ad_)

        var d1:LiveData<List<Ad>> = mUserViewModel.getAdByListIdAd(fav_ad)
            runOnUiThread {
                d1.observe(this, Observer { ad -> adapter.setData(ad) })
            }
        }).start()
    }





}