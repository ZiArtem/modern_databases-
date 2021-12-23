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
import com.example.modern_databases.data.dao.FavoriteDao
import com.example.modern_databases.databinding.AdItemFavoriteBinding
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.ad_item_1.view.imageView3
import kotlinx.android.synthetic.main.ad_item_1.view.price
import kotlinx.android.synthetic.main.ad_item_1.view.title
import kotlinx.android.synthetic.main.ad_item_favorite.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat

interface AdActionListener1 {
    fun onAdDeteils(fav: FavoriteDao.FavoriteAndAdAndImage)

    fun addToCart(fav: FavoriteDao.FavoriteAndAdAndImage)

    fun onFavoriteDelete(fav: FavoriteDao.FavoriteAndAdAndImage)
}

class FavDiffCallback(private val oldList: List<FavoriteDao.FavoriteAndAdAndImage>, private val  newList: List<FavoriteDao.FavoriteAndAdAndImage>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].favorite.id_favorite == newList[newItemPosition].favorite.id_favorite
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldAd = oldList[oldItemPosition].favorite
        val newAd = newList[newItemPosition].favorite

        if (oldAd.id_favorite!=newAd.id_favorite)
            return false
        if (oldAd.id_ad_!=newAd.id_ad_)
            return false
        if (oldAd.id_user_!=newAd.id_user_)
            return false
        return true
    }
}

class FavoriteAdAdapter(private val actionListener: AdActionListener1) :
    RecyclerView.Adapter<FavoriteAdAdapter.MyViewHolder>(), View.OnClickListener {
    private var favList = emptyList<FavoriteDao.FavoriteAndAdAndImage>()

    class MyViewHolder(
        private val binding: AdItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdItemFavoriteBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.imageView3.setOnClickListener(this)
        binding.title.setOnClickListener(this)
        binding.price.setOnClickListener(this)
        binding.buttonAdd.setOnClickListener(this)

        return FavoriteAdAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sdf = SimpleDateFormat("dd.MM HH:mm")
        val currentAd = favList[position].adList[0].ad
        val currentItem = favList[position]

        holder.itemView.title.text = currentAd.title.toString()
        holder.itemView.price.text = DecimalFormat("##.00").format(currentAd.price).toString() + " $"
//        holder.itemView.description.text = currentAd.description.toString()
        holder.itemView.like_button1.isLiked = true

        holder.itemView.tag = currentItem
        holder.itemView.imageView3.tag = currentItem
        holder.itemView.title.tag = currentItem
        holder.itemView.price.tag = currentItem
        holder.itemView.buttonAdd.tag = currentItem

//        holder.itemView.description.tag = currentItem

        if (currentItem.adList[0].imageList.isEmpty()) {
            holder.itemView.imageView3.load("https://ebar.co.za/wp-content/uploads/2018/01/menu-pattern-1-1.png") {
                transformations(RoundedCornersTransformation(40f))
            }
        } else {
            holder.itemView.imageView3.load(currentItem.adList[0].imageList[0].image) {
                transformations(RoundedCornersTransformation(40f))
            }
        }

        holder.itemView.like_button1.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {

            }

            override fun unLiked(likeButton: LikeButton) {
                actionListener.onFavoriteDelete(currentItem)
            }
        })
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    fun setData(fav: List<FavoriteDao.FavoriteAndAdAndImage>) {
        val difUpdate =  DiffUtil.calculateDiff(FavDiffCallback(favList,fav))
        this.favList = fav
        difUpdate.dispatchUpdatesTo(this)
    }

    override fun onClick(v: View) {
        val fav: FavoriteDao.FavoriteAndAdAndImage = v.tag as FavoriteDao.FavoriteAndAdAndImage
        when (v.id) {
            R.id.imageView3 -> {
                actionListener.onAdDeteils(fav)
            }
            R.id.title -> {
                actionListener.onAdDeteils(fav)
            }
            R.id.price -> {
                actionListener.onAdDeteils(fav)
            }
            R.id.buttonAdd -> {
                actionListener.addToCart(fav)
            }


            else -> {
//                actionListener.onAdDeteils(fav)
            }
        }
    }
}