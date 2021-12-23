package com.example.modern_databases.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.modern_databases.R
import com.example.modern_databases.adapters.OrderAdapter
import com.example.modern_databases.adapters.PageAdapter
import com.example.modern_databases.viewmodel.PrViewModel

class New : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        val mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

//        var adapter = OrderAdapter()
//
//        val recyclerView:RecyclerView = findViewById(R.id.recycleviewOrders)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
//
//        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
//        val save_id = sharedPref.getInt("id_user", -1)
//        mUserViewModel.readAllOrders(save_id) .observe(this,  { orders -> adapter.setData(orders) })
    }
}