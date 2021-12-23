package com.example.modern_databases.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modern_databases.R
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.databinding.ItemPageBinding
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.ad_item_1.view.*
import kotlinx.android.synthetic.main.item_page.view.*
import java.text.DecimalFormat


interface PageActionListener {
    fun AdToCart(ad: AdDao.FullAd)

    fun onFavoriteAdd(ad: AdDao.FullAd)
    fun onFavoriteDelete(ad: AdDao.FullAd)

    fun BuyNow(ad: AdDao.FullAd)
}

class PageAdapter(private val actionListener: PageActionListener) :
    RecyclerView.Adapter<PageAdapter.MyViewHolder>(), View.OnClickListener {
    private var ad: List<AdDao.FullAd> = emptyList<AdDao.FullAd>()
    private var id_user = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPageBinding.inflate(inflater, parent, false)
        binding.addToCart.setOnClickListener(this)
        binding.buyNow.setOnClickListener(this)

        return MyViewHolder(binding)
    }

    class MyViewHolder(
        private val binding: ItemPageBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val ad: AdDao.FullAd = v.tag as AdDao.FullAd
        when (v.id) {
            R.id.add_to_cart -> {
                actionListener.AdToCart(ad)
            }
            R.id.buy_now -> {
                actionListener.BuyNow(ad)
            }
            else -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return ad.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.title_2.text = ad[0].ad.title
        holder.itemView.price_2.text = DecimalFormat("##.00").format(ad[0].ad.price).toString() + " $"
        holder.itemView.description_2.text =  ad[0].ad.description.toString()
        holder.itemView.aboutItem.text = ad[0].ad.about_this_item.toString()

        holder.itemView.add_to_cart.tag = ad[0]
        holder.itemView.buy_now.tag = ad[0]

        var f = false
        for(i in ad[position].fav) {
            if (id_user == i.id_user_) {
                f= true
                break
            }
        }

        holder.itemView.like_button_4.isLiked = f

        holder.itemView.like_button_4.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                actionListener.onFavoriteAdd(ad[0])
            }

            override fun unLiked(likeButton: LikeButton) {
                actionListener.onFavoriteDelete(ad[0])
            }
        })

    }

    fun setData(ad_: List<AdDao.FullAd>) {
        ad = ad_
        notifyDataSetChanged()
    }

    fun setUserId (id: Int) {
        id_user = id
    }
}