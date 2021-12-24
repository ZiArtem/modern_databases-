package com.example.modern_databases.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_databases.data.entities.Order
import com.example.modern_databases.databinding.OrderItemBinding
import kotlinx.android.synthetic.main.order_item.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat


interface OrderActionListener {
    fun onAdDeteils(order: Order)
}

class OrderAdapter(private val actionListener: OrderActionListener) :
    RecyclerView.Adapter<OrderAdapter.MyViewHolder>(), View.OnClickListener {
    private var orderList: List<Order> = emptyList<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)

        return MyViewHolder(binding)
    }

    override fun onClick(v: View) {
        val order: Order = v.tag as Order
        when (v.id) {
            else -> {
                actionListener.onAdDeteils(order)
            }
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sdf = SimpleDateFormat("MMM  d  HH:mm")
        val currentItem = orderList[position]

        holder.itemView.priceOrder.text =
            DecimalFormat("##.00").format(currentItem.price).toString() + " $"
        holder.itemView.dataOrder.text = "Order from " + sdf.format(currentItem.date)
        holder.itemView.orderNumber.text = "#" + currentItem.id_order.toString()
        holder.itemView.delivery.text = "delivery from " + currentItem.address.toString()
        holder.itemView.tag = currentItem
    }

    fun setData(order: List<Order>) {
        this.orderList = order
        notifyDataSetChanged()
    }


    class MyViewHolder(
        private val binding: OrderItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}