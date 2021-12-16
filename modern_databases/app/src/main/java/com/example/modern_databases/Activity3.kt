package com.example.modern_databases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_3.view.*
import android.text.TextWatcher
import com.example.modern_databases.data.Favorite


class Activity3 : AppCompatActivity() {
    lateinit var ref: Button
    lateinit var mUserViewModel: UserViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        showAllAd()
        navigationBar()

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

    private fun showAllAd() {
        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        adapter = AdAdapter(object :AdActionListener{
            override fun onAdDeteils(ad:Ad) {
                val intent = Intent(this@Activity3, AdPage::class.java)
                intent.putExtra("id_ad", ad.id_ad.toInt())
                startActivity(intent)
                overridePendingTransition(0, 0);
            }

            override fun onFavoriteAdd(ad: Ad) {
                adFav(ad)
            }
        })
        recyclerView = findViewById(R.id.recycleview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mUserViewModel.readAllAd.observe(this, Observer { ad -> adapter.setData(ad) })
        Thread(Runnable {
            var adList: List<Int> = mUserViewModel.readAllAdId()
            runOnUiThread {
            mUserViewModel.getAllPreviewImage(adList).observe(this, Observer { image -> adapter.setImage(image) })
                mUserViewModel.getAllFavoriteByUser(1)
                    .observe(this, Observer { favorite -> adapter.setFavorite(favorite) })
            }
        }).start()
    }

    private fun navigationBar() {
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

    private fun adFav(ad:Ad) {
        Thread(Runnable {
        var fav= mUserViewModel.getFavoriteById(ad.id_ad)
            if (fav.size==0) {
                mUserViewModel.addFavorite(Favorite(0,ad.id_ad,1))
            }else {
                runOnUiThread {
                mUserViewModel.deleteFavorite(fav[0])
                }
            }
        }).start()
    }
}