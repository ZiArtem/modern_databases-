package com.example.modern_databases.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.R
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.data.dao.OrderItemDao
import com.example.modern_databases.databinding.ItemOrderItemBinding
import kotlinx.android.synthetic.main.item_order_item.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat


class OrderItemAdapter :
    RecyclerView.Adapter<OrderItemAdapter.MyViewHolder>(), View.OnClickListener {
    var orderItemList = emptyList<OrderItemDao.OrderItemAndAdAndImage>()


    class MyViewHolder(
        private val binding: ItemOrderItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderItemBinding.inflate(inflater, parent, false)
//        binding.root.setOnClickListener(this)
//        binding.imageView3.setOnClickListener(this)
//        binding.button2.setOnClickListener(this)

        return MyViewHolder(binding)
    }

    override fun onClick(v: View) {
        val ad: AdDao.FullAd = v.tag as AdDao.FullAd
        when (v.id) {
            R.id.imageView3 -> {
//                actionListener.onAdDeteils(ad)
            }
        }
    }

    override fun getItemCount(): Int {
        return orderItemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sdf = SimpleDateFormat("MM HH:mm")
        val currentItem = orderItemList[position]

        holder.itemView.priceOrderItem.text = DecimalFormat("##.00").format(currentItem.orderItem.price).toString() + " $"
        holder.itemView.titleOrderItem.text = currentItem.adList[0].ad.title

//
//        holder.itemView.title.text = currentItem.ad.title.toString()
//

//        holder.itemView.tag = currentItem
//        holder.itemView.imageView3.tag = currentItem
//        holder.itemView.title.tag = currentItem
//        holder.itemView.price.tag = currentItem
//        holder.itemView.button2.tag = currentItem

        if (currentItem.adList[0].imageList.isEmpty()) {
            holder.itemView.imageOrderItem.load("https://img2.freepng.ru/20180524/hc/kisspng-brand-paper-artikel-manufacturing-gray-camera-5b067144295791.5793394415271488681694.jpg") {
                transformations(RoundedCornersTransformation(40f))
            }
        } else {
            holder.itemView.imageOrderItem.load(currentItem.adList[0].imageList[0].image) {
                transformations(RoundedCornersTransformation(40f))
            }
        }
    }

    fun setData(orderItems: List<OrderItemDao.OrderItemAndAdAndImage>) {
        orderItemList = orderItems
        notifyDataSetChanged()
    }
}