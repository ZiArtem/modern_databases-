package com.example.modern_databases.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.view.*
import android.text.TextWatcher
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.modern_databases.*
import com.example.modern_databases.data.*
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.data.data_class.Favorite
import com.example.modern_databases.viewmodel.PrViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var mUserViewModel: PrViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigationBar()
        showAllAd()
        changeRequest()
    }

    private fun showAllAd() {
        mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        adapter = AdAdapter(object : AdActionListener {
            override fun onAdDeteils(ad: AdDao.FullAd) {
                val intent = Intent(this@HomeActivity, AdPageActivity::class.java)
                intent.putExtra("id_ad", ad.ad.id_ad.toInt())

                startActivity(intent)
                overridePendingTransition(0, 0)
            }

            override fun onFavoriteAdd(ad: AdDao.FullAd) {
                mUserViewModel.addFavorite(Favorite(0, ad.ad.id_ad, 1))
            }

            override fun onFavoriteDelete(ad: AdDao.FullAd) {
                mUserViewModel.deleteFavorite(ad.fav[0])
            }
        })

        recyclerView = findViewById(R.id.recycleview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val itemAnimator = recyclerView.itemAnimator
        if (itemAnimator is DefaultItemAnimator)
            itemAnimator.supportsChangeAnimations = false

//        mUserViewModel.readAllAd.observe(this, Observer { ad -> adapter.setData(ad) })
        Thread(Runnable {
            var a1 = mUserViewModel.TestALlAd()
            runOnUiThread {
                a1.observe(this, Observer { adList -> adapter.setData(adList) })

            }
//            var adIdList: List<Int> = mUserViewModel.readAllAdId()
//            runOnUiThread {
//            mUserViewModel.getAllPreviewImage(adIdList).observe(this, Observer { image -> adapter.setImage(image) })
//                mUserViewModel.getAllFavoriteByUser(1)
//                    .observe(this, Observer { favorite -> adapter.setFavorite(favorite) })
//            }
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
                    val intent = Intent(this, TestActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);
                }
            }
            true
        }
    }

    private fun changeRequest() {
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
//                searchDatabase(s.toString())
            }
        })
    }

//    private fun searchDatabase(query: String) {
//        val searchQuery = "$query"
////        var ad1: LiveData<List<Ad>> = mUserViewModel.getByKeyword(searchQuery)
////        ad1.observe(this, {ad-> adapter.setData(ad) })
//
//        if (query != null)
//            mUserViewModel.getByKeyword(searchQuery).observe(this, { ad -> adapter.setData(ad) })
//        else
//            mUserViewModel.readAllAd.observe(this, Observer { ad -> adapter.setData(ad) })
////        mUserViewModel.readAllAd.observe(this, Observer {ad-> adapter.setData(ad) })
//    }

}