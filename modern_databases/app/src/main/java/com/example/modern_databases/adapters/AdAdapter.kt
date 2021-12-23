package com.example.modern_databases.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.R
import com.example.modern_databases.data.dao.AdDao
import com.example.modern_databases.databinding.AdItem1Binding
import kotlinx.android.synthetic.main.ad_item_1.view.*
import java.text.SimpleDateFormat
import com.like.LikeButton

import com.like.OnLikeListener
import java.text.DecimalFormat

interface AdActionListener {
    fun onAdDeteils(ad: AdDao.FullAd)
    fun onFavoriteAdd(ad: AdDao.FullAd)
    fun onFavoriteDelete(ad: AdDao.FullAd)
    fun buy (ad: AdDao.FullAd)
}

class AdDiffCallback(private val oldList: List<AdDao.FullAd>, private val  newList: List<AdDao.FullAd>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].ad.id_ad == newList[newItemPosition].ad.id_ad
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldAd = oldList[oldItemPosition].ad
        val newAd = newList[newItemPosition].ad

        if (oldAd.id_ad!=newAd.id_ad)
            return false
        if (oldAd.title!=newAd.title)
            return false
        if (oldAd.price!=newAd.price)
            return false
        if (oldAd.category!=newAd.category)
            return false
        if (oldAd.date!=newAd.date)
            return false
        if (oldAd.id_user_!=newAd.id_user_)
            return false
        if (oldAd.description!=newAd.description)
            return false
        if (oldList[oldItemPosition].fav!=newList[newItemPosition].fav)
            return false

        return true
    }
}

class AdAdapter(private val actionListener: AdActionListener) :
    RecyclerView.Adapter<AdAdapter.MyViewHolder>(), View.OnClickListener {
    private var adList = emptyList<AdDao.FullAd>()
    private var id_user = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdItem1Binding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.imageView3.setOnClickListener(this)
        binding.button2.setOnClickListener(this)

        return MyViewHolder(binding)
    }

    override fun onClick(v: View) {
        val ad:AdDao.FullAd = v.tag as AdDao.FullAd
        when (v.id) {
            R.id.imageView3 -> {
                actionListener.onAdDeteils(ad)
            }
            R.id.button2 -> {
                actionListener.buy(ad)
            }
            else -> {
                actionListener.onAdDeteils(ad)
            }
        }
    }

    override fun getItemCount(): Int {
        return adList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sdf = SimpleDateFormat("MM HH:mm")
        val currentItem = adList[position]

        holder.itemView.title.text = currentItem.ad.title.toString()
        holder.itemView.price.text =DecimalFormat("##.00").format(currentItem.ad.price).toString() + " $"

        holder.itemView.tag = currentItem
        holder.itemView.imageView3.tag = currentItem
        holder.itemView.title.tag = currentItem
        holder.itemView.price.tag = currentItem
        holder.itemView.button2.tag = currentItem

        if (currentItem.imageList.isEmpty()) {
            holder.itemView.imageView3.load("https://img2.freepng.ru/20180524/hc/kisspng-brand-paper-artikel-manufacturing-gray-camera-5b067144295791.5793394415271488681694.jpg") {
                transformations(RoundedCornersTransformation(40f))
            }
        } else {
            holder.itemView.imageView3.load(currentItem.imageList[0].image) {
                transformations(RoundedCornersTransformation(40f))
            }
        }

        var f = false
        for(i in currentItem.fav) {
            if (id_user == i.id_user_) {
                f= true
                break
            }
        }

        holder.itemView.like_button.isLiked = f

        holder.itemView.like_button.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                actionListener.onFavoriteAdd(currentItem)
            }

            override fun unLiked(likeButton: LikeButton) {
                actionListener.onFavoriteDelete(currentItem)
            }
        })
    }

    fun setData(ad_: List<AdDao.FullAd>) {
        val difUpdate =  DiffUtil.calculateDiff(AdDiffCallback(adList,ad_))
        this.adList = ad_
        difUpdate.dispatchUpdatesTo(this)
//    notifyDataSetChanged()
    }

    fun setUserId (id: Int) {
        id_user = id
    }


    class MyViewHolder(
        private val binding: AdItem1Binding
    ) : RecyclerView.ViewHolder(binding.root)
}