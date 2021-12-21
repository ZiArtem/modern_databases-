package com.example.modern_databases.adapters

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

interface AdActionListener {
    fun onAdDeteils(ad: AdDao.FullAd)

    fun onFavoriteAdd(ad: AdDao.FullAd)

    fun buy (ad: AdDao.FullAd)

    fun onFavoriteDelete(ad: AdDao.FullAd)
}

class AdDiffCallback(private val oldList: List<AdDao.FullAd>, private val  newList: List<AdDao.FullAd>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldAd = oldList[oldItemPosition]
        val newAd = newList[oldItemPosition]
        return oldAd.ad.id_ad == newAd.ad.id_ad
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldAd = oldList[oldItemPosition]
        val newAd = newList[oldItemPosition]
        var f = true
        if (oldAd.ad!= newAd.ad)
            f= false
        if (oldAd.imageList!= newAd.imageList)
            f= false
        if (oldAd.fav!= newAd.fav)
            f= false
        return f
    }
}

class AdAdapter(private val actionListener: AdActionListener) :
    RecyclerView.Adapter<AdAdapter.MyViewHolder>(), View.OnClickListener {
    private var adList = emptyList<AdDao.FullAd>()

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
        val sdf = SimpleDateFormat("dd.MM HH:mm")
        val currentItem = adList[position]
        holder.itemView.title.text = currentItem.ad.title.toString()
        holder.itemView.price.text = currentItem.ad.price.toString() + " руб."

        holder.itemView.tag = currentItem
        holder.itemView.imageView3.tag = currentItem
        holder.itemView.title.tag = currentItem
        holder.itemView.price.tag = currentItem
        holder.itemView.button2.tag = currentItem

        if (currentItem.imageList.isEmpty()) {
            holder.itemView.imageView3.load("https://sun9-48.userapi.com/impg/EB14wX5yUN8VgQ3got22hTt75eEEo4RcXVUkAg/Qf8qM4DDbGo.jpg?size=1397x247&quality=96&sign=ad618a5499a12d3d24c3eafdf78755a6&type=album") {
                transformations(RoundedCornersTransformation(40f))
            }
        } else {
            holder.itemView.imageView3.load(currentItem.imageList[0].image) {
                transformations(RoundedCornersTransformation(40f))
            }
        }

        holder.itemView.like_button.isLiked = currentItem.fav.isNotEmpty()

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
    }

    class MyViewHolder(
        private val binding: AdItem1Binding
    ) : RecyclerView.ViewHolder(binding.root)
}