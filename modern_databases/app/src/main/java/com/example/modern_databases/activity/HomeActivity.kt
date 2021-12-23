package com.example.modern_databases.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.modern_databases.*
import com.example.modern_databases.adapters.AdActionListener
import com.example.modern_databases.adapters.AdAdapter
import com.example.modern_databases.data.*
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.data.entities.Ad
import com.example.modern_databases.data.entities.Cart
import com.example.modern_databases.data.entities.Favorite
import com.example.modern_databases.viewmodel.PrViewModel
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var mUserViewModel: PrViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdAdapter
    private var save_id_user = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPref: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        save_id_user = sharedPref.getInt("id_user", -1)

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
//                val intent = Intent(this@HomeActivity, New::class.java)
                intent.putExtra("id_ad", ad.ad.id_ad.toInt())

                startActivity(intent)
                overridePendingTransition(0, 0)
            }


            override fun buy(ad: AdDao.FullAd) {
                Thread(Runnable {
                    val cartItem = mUserViewModel.getCartByIdAd(ad.ad.id_ad)
                    if (cartItem.isEmpty()) {
                        mUserViewModel.addCartElement(Cart(0, 1, Date(), ad.ad.id_ad, save_id_user))
                    } else {
                        mUserViewModel.updateCartElement(
                            Cart(
                                cartItem[0].id_cart,
                                cartItem[0].num + 1,
                                cartItem[0].date,
                                cartItem[0].id_ad_,
                                cartItem[0].id_user_
                            )
                        )
                    }
                }).start()
            }

            override fun onFavoriteAdd(ad: AdDao.FullAd) {
                mUserViewModel.addFavorite(Favorite(0, ad.ad.id_ad, save_id_user))
            }

            override fun onFavoriteDelete(ad: AdDao.FullAd) {
                for (i in ad.fav)
                    if (i.id_ad_ == ad.ad.id_ad) {
                        mUserViewModel.deleteFavorite(i)
                        break
                    }
            }
        })

        recyclerView = findViewById(R.id.recycleview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter.setUserId(save_id_user)
        mUserViewModel.TestALlAd().observe(this, Observer { adList -> adapter.setData(adList) })
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

                R.id.cart -> {
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
                    val intent = Intent(this, New::class.java)
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
                searchDatabase(s.toString())
            }
        })
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "$query"

        if (query != null)
            mUserViewModel.getByKeyword(searchQuery).observe(this, { ad -> adapter.setData(ad) })
        else
            mUserViewModel.TestALlAd().observe(this, Observer { adList -> adapter.setData(adList) })
    }
}