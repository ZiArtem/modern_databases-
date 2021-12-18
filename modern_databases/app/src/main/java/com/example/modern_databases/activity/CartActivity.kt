package com.example.modern_databases.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
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
import com.example.modern_databases.data.dao.FullAd1
import com.example.modern_databases.data.data_class.Favorite
import com.example.modern_databases.viewmodel.PrViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : AppCompatActivity() {
    lateinit var mUserViewModel: PrViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var price:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        showCart()
        price1()

        price = findViewById(R.id.allPrice)
        val del:TextView = findViewById(R.id.deleteALlElement)
        del.setOnClickListener { delete() }

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
            override fun onAdDeteils(cart: FullAd1) {
                val intent = Intent(this@CartActivity, AdPageActivity::class.java)
                intent.putExtra("id_ad", cart.cart.id_ad_.toInt())

                startActivity(intent)
                overridePendingTransition(0, 0)
            }

            override fun buy(cart: FullAd1) {
                TODO("Not yet implemented")
            }

            override fun deleteItem(cart: FullAd1) {
                Thread(Runnable {
                var cart1 = mUserViewModel.getCartByIdAd(cart.cart.id_ad_)
                    mUserViewModel.deleteCartElement(cart1[0])
                }).start()
            }
        })
        recyclerView = findViewById(R.id.recycleviewCart)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        mUserViewModel.getAllElementOnCartTest1(1).observe(this, Observer { cart -> adapter.setData(cart) })
    }

    private fun price1() {
        Thread(Runnable {
            var allPrice = 0
            var c = mUserViewModel.getAllElementOnCartTest(1)

            for (i in c) {

                var ad1 = mUserViewModel.getAdById(i.id_ad_)

                allPrice += i.num * ad1[0].price
            }
            runOnUiThread {
                price.setText("Всего " + allPrice.toString()+" Руб.")
            }
        }).start()
    }

    private fun delete() {
        Thread(Runnable {
            var cart = mUserViewModel.getAllElementOnCartTest(1)
            for (i in cart) {
                mUserViewModel.deleteCartElement(i)
            }
        }).start()
        price.setText("Всего 0 руб.")
    }
}