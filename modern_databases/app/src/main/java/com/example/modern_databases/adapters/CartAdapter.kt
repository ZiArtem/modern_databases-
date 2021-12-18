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
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.data.data_class.Cart
import com.example.modern_databases.databinding.CartItemBinding
import kotlinx.android.synthetic.main.ad_item_favorite.view.*
import kotlinx.android.synthetic.main.cart_item.view.*


interface CartActionListener2 {
    fun onAdDeteils(ad: AdDao.FullAd)

    fun buy(ad: AdDao.FullAd)
}

class CartAdapter(private val actionListener: CartActionListener2) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder2>(), View.OnClickListener {
    private var cartList = emptyList<AdDao.FullAd>()
    private var cart = emptyList<Cart>()

    class MyViewHolder2(
        val binding: CartItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CartItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
//        binding.i.setOnClickListener(this)

        return CartAdapter.MyViewHolder2(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        val currentItem = cart[position]

//        holder.itemView.titleCart.text = currentItem.id_ad_.toString()
//        holder.itemView.priceCart.text = currentItem.id_ad_.toString()
//        holder.itemView.descriptionCart.text = currentItem.id_ad_.toString()


//        val currentItem = cartList[position]
//        holder.itemView.titleCart.text = currentItem.ad.title.toString()
//        holder.itemView.priceCart.text = currentItem.ad.price.toString() + " руб."
//        holder.itemView.descriptionCart.text = currentItem.ad.description.toString()
//
//        holder.itemView.tag = currentItem
//        holder.itemView.imageCart.tag = currentItem
//        holder.itemView.titleCart.tag = currentItem
//        holder.itemView.priceCart.tag = currentItem
//        holder.itemView.description.tag = currentItem
//
        holder.itemView.imageCart.load("https://ebar.co.za/wp-content/uploads/2018/01/menu-pattern-1-1.png") {
            transformations(RoundedCornersTransformation(40f))
        }

//        if (currentItem.imageList.isEmpty()) {
//            holder.itemView.imageCart.load("https://ebar.co.za/wp-content/uploads/2018/01/menu-pattern-1-1.png") {
//                transformations(RoundedCornersTransformation(40f))
//            }
//        } else {
//            holder.itemView.imageCart.load(currentItem.imageList[0].image) {
//                transformations(RoundedCornersTransformation(40f))
//            }
//        }
    }

    override fun getItemCount(): Int {
        return cart.size
    }

//    fun setData(cart_: List<AdDao.FullAd>) {
//        val difUpdate =  DiffUtil.calculateDiff(AdDiffCallback(cartList,cart_))
//        this.cartList = cart_
////        notifyDataSetChanged()
//        difUpdate.dispatchUpdatesTo(this)
//    }

    fun setDataCart(cart_: List<Cart>) {
//        val difUpdate =  DiffUtil.calculateDiff(AdDiffCallback(cartList,cart_))
//        this.cartList = cart_
        this.cart = cart_
//        notifyDataSetChanged()
        notifyDataSetChanged()
//        difUpdate.dispatchUpdatesTo(this)
    }


    override fun onClick(v: View) {
//        val ad: AdDao.FullAd = v.tag as AdDao.FullAd
//        when (v.id) {
//            R.id.imageView3 -> {
//                actionListener.onAdDeteils(ad)
//            }
//            else -> {
//                actionListener.onAdDeteils(ad)
//            }
//        }
    }
}