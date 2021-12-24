package com.example.modern_databases.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.modern_databases.R
import com.example.modern_databases.adapters.AdActionListener
import com.example.modern_databases.adapters.OrderAdapter
import com.example.modern_databases.adapters.OrderItemActionListener
import com.example.modern_databases.adapters.PageAdapter
import com.example.modern_databases.data.entities.Order
import com.example.modern_databases.viewmodel.PrViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class New : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        val mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        var adapter = OrderAdapter(object : OrderItemActionListener{
            override fun onAdDeteils(order: Order) {
                val intent = Intent(this@New, OrderItemActivity::class.java)
                intent.putExtra("id_order", order.id_order.toInt())

                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        })

        val recyclerView:RecyclerView = findViewById(R.id.recycleviewOrders)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val save_id = sharedPref.getInt("id_user", -1)
        mUserViewModel.readAllOrders(save_id) .observe(this,  { orders -> adapter.setData(orders) })

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.order
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
                 R.id.cart-> {
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
//                    val intent = Intent(this, TestActivity::class.java)
//                    startActivity(intent)
//                    overridePendingTransition(0, 0);
                }
            }
            true
        }


    }
}