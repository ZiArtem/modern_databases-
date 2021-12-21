package com.example.modern_databases.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.AdActionListener
import com.example.modern_databases.AdAdapter
import com.example.modern_databases.AdDiffCallback
import com.example.modern_databases.R
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.data.data_class.Ad
import com.example.modern_databases.data.data_class.Image
import com.example.modern_databases.databinding.AdItem1Binding
import com.example.modern_databases.databinding.AdItemFavoriteBinding
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.ad_item_1.view.*
import kotlinx.android.synthetic.main.ad_item_1.view.imageView3
import kotlinx.android.synthetic.main.ad_item_1.view.price
import kotlinx.android.synthetic.main.ad_item_1.view.title
import kotlinx.android.synthetic.main.ad_item_favorite.view.*
import java.text.SimpleDateFormat


interface AdActionListener1 {
    fun onAdDeteils(ad: AdDao.FullAd)

    fun onFavoriteAdd(ad: AdDao.FullAd)

    fun buy(ad: AdDao.FullAd)

    fun onFavoriteDelete(ad: AdDao.FullAd)
}

class FavoriteAdAdapter(private val actionListener: AdActionListener1) :
    RecyclerView.Adapter<FavoriteAdAdapter.MyViewHolder1>(), View.OnClickListener {
    private var adList = emptyList<AdDao.FullAd>()

    class MyViewHolder1(
        val binding: AdItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdItemFavoriteBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.imageView3.setOnClickListener(this)

        return FavoriteAdAdapter.MyViewHolder1(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {
        val sdf = SimpleDateFormat("dd.MM HH:mm")
        val currentItem = adList[position]
        holder.itemView.title.text = currentItem.ad.title.toString()
        holder.itemView.price.text = currentItem.ad.price.toString() + " руб."
        holder.itemView.description.text = currentItem.ad.description.toString()
        holder.itemView.like_button1.setLiked(true)

        holder.itemView.tag = currentItem
        holder.itemView.imageView3.tag = currentItem
        holder.itemView.title.tag = currentItem
        holder.itemView.price.tag = currentItem
        holder.itemView.description.tag = currentItem

        if (currentItem.imageList.isEmpty()) {
            holder.itemView.imageView3.load("https://ebar.co.za/wp-content/uploads/2018/01/menu-pattern-1-1.png") {
                transformations(RoundedCornersTransformation(40f))
            }
        } else {
            holder.itemView.imageView3.load(currentItem.imageList[0].image) {
                transformations(RoundedCornersTransformation(40f))
            }
        }

        holder.itemView.like_button1.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                actionListener.onFavoriteAdd(currentItem)
            }

            override fun unLiked(likeButton: LikeButton) {
                actionListener.onFavoriteDelete(currentItem)
            }
        })
    }

    override fun getItemCount(): Int {
        return adList.size
    }

    fun setData(ad_: List<AdDao.FullAd>) {
        val difUpdate =  DiffUtil.calculateDiff(AdDiffCallback(adList,ad_))
        this.adList = ad_
//        notifyDataSetChanged()
        difUpdate.dispatchUpdatesTo(this)
    }

    override fun onClick(v: View) {
        val ad: AdDao.FullAd = v.tag as AdDao.FullAd
        when (v.id) {
            R.id.imageView3 -> {
                actionListener.onAdDeteils(ad)
            }
            else -> {
                actionListener.onAdDeteils(ad)
            }
        }
    }
}