package com.example.modern_databases.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.modern_databases.R
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

//        var adapter = PageAdapter()

//        val recyclerView:RecyclerView = findViewById(R.id.recycleviewTest)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }
}