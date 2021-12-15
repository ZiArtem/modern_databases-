package com.example.modern_databases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_3.view.*

class Activity3 : AppCompatActivity() {
    lateinit var  ref: Button
    lateinit var mUserViewModel: UserViewModel
    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        val adapter =AdAdapter()
        recyclerView = findViewById(R.id.recycleview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mUserViewModel.readAllAd.observe(this, Observer {ad-> adapter.setData(ad) })
        mUserViewModel.getAllImage.observe(this, Observer { image-> adapter.setImage(image)})

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item->
            when(item.itemId) {
                R.id.home-> {
                    val intent = Intent(this, Activity3::class.java)
                    startActivity(intent)
                }
                R.id.favorite-> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                }
                R.id.orders-> {
                    val intent = Intent(this, OrdersActivity::class.java)
                    startActivity(intent)
                }
                R.id.settings-> {
                    val intent = Intent(this, Activity2::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }
}