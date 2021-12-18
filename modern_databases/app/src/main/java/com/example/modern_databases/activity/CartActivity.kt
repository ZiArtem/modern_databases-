package com.example.modern_databases.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_databases.R
import com.example.modern_databases.adapters.AdActionListener1
import com.example.modern_databases.adapters.CartActionListener2
import com.example.modern_databases.adapters.CartAdapter
import com.example.modern_databases.adapters.FavoriteAdAdapter
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.data.data_class.Favorite
import com.example.modern_databases.viewmodel.PrViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : AppCompatActivity() {
    lateinit var mUserViewModel: PrViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        showCart()

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.cart)
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
//                 R.id.cart-> {
//                    val intent = Intent(this, OrdersActivity::class.java)
//                    startActivity(intent)
//                }
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

    private fun showCart() {
        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        adapter = CartAdapter(object : CartActionListener2 {
            override fun onAdDeteils(ad: AdDao.FullAd) {
//                val intent = Intent(this@CartActivity, AdPageActivity::class.java)
//                intent.putExtra("id_ad", ad.ad.id_ad.toInt())
//
//                startActivity(intent)
//                overridePendingTransition(0, 0)
            }
            override fun buy(ad: AdDao.FullAd) {
//                Toast.makeText(
//                    applicationContext,
//                    "this feature hasn't been implemented yet",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

        })
        recyclerView = findViewById(R.id.recycleviewCart)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        mUserViewModel.getAllElementOnCart(1).observe(this, Observer { cart -> adapter.setDataCart(cart) })
    }

}