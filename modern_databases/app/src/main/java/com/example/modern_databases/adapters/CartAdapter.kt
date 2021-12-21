package com.example.modern_databases.adapters

import com.example.modern_databases.databinding.ActivityCartBinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.AdDiffCallback
import com.example.modern_databases.R
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.data.dao.FullAd1
import com.example.modern_databases.data.data_class.Cart
import com.example.modern_databases.databinding.CartItemBinding
import kotlinx.android.synthetic.main.ad_item_favorite.view.*
import kotlinx.android.synthetic.main.cart_item.view.*


interface CartActionListener2 {
    fun onAdDeteils(cart: FullAd1)

    fun deleteItem(cart: FullAd1)

    fun plusItem(cart: FullAd1)
    fun minusItem(cart: FullAd1)

}

class CartAdapter(private val actionListener: CartActionListener2) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder2>(), View.OnClickListener {
    private var cartList = emptyList<FullAd1>()

    class MyViewHolder2(
        val binding: CartItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CartItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.imageCart.setOnClickListener(this)
        binding.titleCart.setOnClickListener(this)
        binding.priceCart.setOnClickListener(this)
        binding.deleteItem.setOnClickListener(this)
        binding.plus.setOnClickListener(this)
        binding.minus.setOnClickListener(this)

        return CartAdapter.MyViewHolder2(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        val currentItem = cartList[position]

        holder.itemView.titleCart.text = currentItem.f[0].ad.title.toString()
        holder.itemView.priceCart.setText(currentItem.f[0].ad.price.toString() + " Руб.")
        holder.itemView.num.setText(currentItem.cart.num.toString())


        holder.itemView.tag = currentItem
        holder.itemView.imageCart.tag = currentItem
        holder.itemView.titleCart.tag = currentItem
        holder.itemView.priceCart.tag = currentItem
        holder.itemView.deleteItem.tag = currentItem
        holder.itemView.plus.tag = currentItem
        holder.itemView.minus.tag = currentItem


        if (currentItem.f[0].imageList.isEmpty()) {
            holder.itemView.imageCart.load("https://ebar.co.za/wp-content/uploads/2018/01/menu-pattern-1-1.png") {
                transformations(RoundedCornersTransformation(40f))
            }
        } else {
            holder.itemView.imageCart.load(currentItem.f[0].imageList[0].image) {
                transformations(RoundedCornersTransformation(40f))
            }
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun setData(cart_: List<FullAd1>) {
//        val difUpdate =  DiffUtil.calculateDiff(AdDiffCallback(cartList,cart_))
        this.cartList = cart_
        notifyDataSetChanged()
//        difUpdate.dispatchUpdatesTo(this)
    }

    override fun onClick(v: View) {
        val cart: FullAd1 = v.tag as FullAd1
        when (v.id) {
            R.id.imageCart -> {
                actionListener.onAdDeteils(cart)
            }
            R.id.titleCart -> {
                actionListener.onAdDeteils(cart)
            }
            R.id.priceCart -> {
                actionListener.onAdDeteils(cart)
            }

            R.id.deleteItem -> {
                actionListener.deleteItem(cart)
            }
            R.id.plus -> {
                actionListener.plusItem(cart)
            }
            R.id.minus -> {
                actionListener.minusItem(cart)
            }

            else -> {
//                actionListener.onAdDeteils(cart)
            }
        }
    }
}