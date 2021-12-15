package com.example.modern_databases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_3.view.*
import android.text.TextWatcher


class Activity3 : AppCompatActivity() {
    lateinit var ref: Button
    lateinit var mUserViewModel: UserViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        adapter = AdAdapter()
        recyclerView = findViewById(R.id.recycleview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mUserViewModel.readAllAd.observe(this, Observer { ad -> adapter.setData(ad) })
        mUserViewModel.getAllImage.observe(this, Observer { image -> adapter.setImage(image) })

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
//                    val intent = Intent(this, Activity3::class.java)
//                    startActivity(intent)
                }
                R.id.favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
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

        var editText: EditText = findViewById(R.id.search_ed)
        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                searchDatabase(s.toString())
            }
        })
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "$query"
//        var ad1: LiveData<List<Ad>> = mUserViewModel.getByKeyword(searchQuery)
//        ad1.observe(this, {ad-> adapter.setData(ad) })

        if (query != null)
            mUserViewModel.getByKeyword(searchQuery).observe(this, { ad -> adapter.setData(ad) })
        else
            mUserViewModel.readAllAd.observe(this, Observer { ad -> adapter.setData(ad) })
//        mUserViewModel.readAllAd.observe(this, Observer {ad-> adapter.setData(ad) })
    }
}