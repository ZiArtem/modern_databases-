package com.example.modern_databases.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.modern_databases.R
import com.example.modern_databases.adapters.CartActionListener
import com.example.modern_databases.adapters.CartAdapter
import com.example.modern_databases.data.dao.FullAd1
import com.example.modern_databases.data.entities.Cart
import com.example.modern_databases.viewmodel.PrViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class CartActivity : AppCompatActivity() {
    lateinit var mUserViewModel: PrViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var price: TextView
    private var save_id_user = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        save_id_user = sharedPref.getInt("id_user", -1)

        showCart()
        price1()

        price = findViewById(R.id.allPrice)
        val del: TextView = findViewById(R.id.deleteALlElement)
        del.setOnClickListener { delete() }

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.cart
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
                R.id.order -> {
//                    val intent = Intent(this, TestActivity::class.java)
//                    startActivity(intent)
//                    overridePendingTransition(0, 0);
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

        adapter = CartAdapter(object : CartActionListener {
            override fun onAdDeteils(cart: FullAd1) {
                val intent = Intent(this@CartActivity, AdPageActivity::class.java)
                intent.putExtra("id_ad", cart.cart.id_ad_.toInt())

                startActivity(intent)
                overridePendingTransition(0, 0)
            }

            override fun deleteItem(cart: FullAd1) {
                Thread(Runnable {
                    var cart1 = mUserViewModel.getCartByIdAd(cart.cart.id_ad_)
                    mUserViewModel.deleteCartElement(cart1[0])
                }).start()
                lifecycleScope.launch {
                    delay(100)
                    price1()
                }
            }

            override fun plusItem(cart: FullAd1) {
                Thread(Runnable {
                    val cartItem = mUserViewModel.getCartByIdAd(cart.cart.id_ad_)

                    mUserViewModel.updateCartElement(
                        Cart(
                            cartItem[0].id_cart,
                            cartItem[0].num + 1,
                            cartItem[0].date,
                            cartItem[0].id_ad_,
                            cartItem[0].id_user_
                        )
                    )

                }).start()

                lifecycleScope.launch {
                    delay(100)
                    price1()
                }

            }

            override fun minusItem(cart: FullAd1) {
                Thread(Runnable {
                    val cartItem = mUserViewModel.getCartByIdAd(cart.cart.id_ad_)
                    if (cartItem[0].num == 1) {
                        mUserViewModel.deleteCartElement(cartItem[0])
                        price1()
                    } else {
                        mUserViewModel.updateCartElement(
                            Cart(
                                cartItem[0].id_cart,
                                cartItem[0].num - 1,
                                cartItem[0].date,
                                cartItem[0].id_ad_,
                                cartItem[0].id_user_
                            )
                        )
                        price1()
                    }
                }).start()
                lifecycleScope.launch {
                    delay(100)
                    price1()
                }
            }
        })
        recyclerView = findViewById(R.id.recycleviewCart)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        mUserViewModel.getAllElementOnCartTest1(save_id_user)
            .observe(this, Observer { cart -> adapter.setData(cart) })
    }

    private fun price1() {
        Thread(Runnable {
            var allPrice = 0.0
            var c = mUserViewModel.getAllElementOnCartTest(save_id_user)

            for (i in c) {

                var ad1 = mUserViewModel.getAdById(i.id_ad_)

                allPrice += i.num * ad1[0].price
            }
            runOnUiThread {
                price.text = "All " + allPrice.toString() + " $"
            }
        }).start()
    }

    private fun delete() {
        Thread(Runnable {
            var cart = mUserViewModel.getAllElementOnCartTest(save_id_user)
            for (i in cart) {
                mUserViewModel.deleteCartElement(i)
            }
        }).start()
        price.text = "All 0 $"
    }
}