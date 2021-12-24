package com.example.modern_databases.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.modern_databases.R
import com.example.modern_databases.adapters.OrderActionListener
import com.example.modern_databases.adapters.OrderAdapter
import com.example.modern_databases.adapters.OrderItemActionListener
import com.example.modern_databases.adapters.OrderItemAdapter
import com.example.modern_databases.data.dao.OrderItemDao
import com.example.modern_databases.viewmodel.PrViewModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class OrderItemActivity : AppCompatActivity() {
    private var id_order = -1
    private lateinit var orderNum: TextView
    private lateinit var TotalCost: TextView
    private lateinit var from: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_item)

        val intent = intent
        id_order = intent.getIntExtra("id_order", 0)

        val mUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PrViewModel::class.java)

        orderNum = findViewById(R.id.OrderNumberI)
        TotalCost = findViewById(R.id.TotalCost)
        from = findViewById(R.id.dateOrder)

        var adapter = OrderItemAdapter(object : OrderItemActionListener {
            override fun onAdDeteils(orderItems: OrderItemDao.OrderItemAndAdAndImage) {
                val intent = Intent(this@OrderItemActivity, AdPageActivity::class.java)
                intent.putExtra("id_ad", orderItems.adList[0].ad.id_ad.toInt())

                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        })

        val recyclerView: RecyclerView = findViewById(R.id.orderItemRecycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        mUserViewModel.getOrderItemByIdOrder(id_order)
            .observe(this, { ordersItem -> adapter.setData(ordersItem) })
        Thread(Runnable {
            val order = mUserViewModel.getOrderById(id_order)
            orderNum.text = "Order #" + order[0].id_order.toString()
            TotalCost.text =
                "Total cost " + DecimalFormat("##.00").format(order[0].price).toString() + " $"
            val sdf = SimpleDateFormat("MMM  d  HH:mm")
            from.text = "from " + sdf.format(order[0].date).toString()
        }).start()
    }
}