package com.example.modern_databases

import android.content.Context
import android.content.Intent
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.Favorite
import com.example.modern_databases.data.Image
import com.example.modern_databases.databinding.AdItem1Binding
import kotlinx.android.synthetic.main.ad_item_1.view.*
import java.text.SimpleDateFormat
import com.like.LikeButton

import com.like.OnLikeListener

interface AdActionListener {
    fun onAdDeteils(ad:Ad)

    fun onFavoriteAdd (ad:Ad)
}

class AdAdapter(private val actionListener:AdActionListener) : RecyclerView.Adapter<AdAdapter.MyViewHolder>(),View.OnClickListener {
    private var adList = emptyList<Ad>()
    private var image = emptyList<Image>()
    private var favorite = emptyList<Favorite>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdItem1Binding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.imageView3.setOnClickListener(this)
//        binding.likeButton.setOnLikeListener(object : OnLikeListener {
//            override fun liked(likeButton: LikeButton) {
//
//
////                var mUserViewModel = ViewModelProvider(
////
////                ).get(UserViewModel::class.java)
//            }
//
//            override fun unLiked(likeButton: LikeButton) {
//            }
//        })

        binding.likeButton.setOnClickListener(this)
        return MyViewHolder(binding)

//        return MyViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.ad_item_1, parent, false)
//        )
    }

    override fun onClick(v: View) {
       val ad:Ad = v.tag as Ad
        when (v.id) {
            R.id.imageView3-> {
                actionListener.onAdDeteils(ad)
            }

            R.id.like_button-> {
                actionListener.onFavoriteAdd(ad)
            }
            else -> {

            }
        }
    }

    override fun getItemCount(): Int {
        return adList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sdf = SimpleDateFormat("dd.MM HH:mm")
        val currentItem = adList[position]
        holder.itemView.title.text = currentItem.title.toString()
        holder.itemView.price.text = currentItem.price.toString() + " руб."
//        holder.itemView.date.text  =  sdf.format(currentItem.date)

        holder.itemView.tag = currentItem
        holder.itemView.imageView3.tag = currentItem
        holder.itemView.like_button.tag = currentItem


        var i: Int = 0
        for (item in image) {
            if (item.id_ad_ == currentItem.id_ad) {
                holder.itemView.imageView3.load(item.image) {
                    transformations(RoundedCornersTransformation(40f))
                }
                break
            }
            i += 1
        }

        if (i == image.size) {
            holder.itemView.imageView3.load("https://ebar.co.za/wp-content/uploads/2018/01/menu-pattern-1-1.png") {
                transformations(RoundedCornersTransformation(40f))
            }
        }
        i = 0
        for (j in favorite) {
            if (currentItem.id_ad == j.id_ad_) {
                holder.itemView.like_button.setLiked(true)
                break
            }
            i++
        }
        if (i == favorite.size)
            holder.itemView.like_button.setLiked(false)

//        holder.itemView.imageView3.setOnClickListener {
//            Toast.makeText(
//                context,
//                "press",
//                Toast.LENGTH_SHORT
//            ).show()
////            var intent = Intent(context,FavoriteActivity::class.java)
////            context.startActivities(intent)
//        }

        holder.itemView.like_button.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
//                Toast.makeText(
//                    context ,
//                    "press",
//                    Toast.LENGTH_SHORT
//                ).show()

//                var mUserViewModel = ViewModelProvider(
//
//                ).get(UserViewModel::class.java)
            }

            override fun unLiked(likeButton: LikeButton) {
                holder.itemView.price.text = (currentItem.price).toString() + " руб."
            }
        })
    }

    fun setData(ad: List<Ad>) {
        this.adList = ad

        notifyDataSetChanged()
    }

    fun setImage(image: List<Image>) {
        this.image = image
        notifyDataSetChanged()
    }

    fun setFavorite(favorite: List<Favorite>) {
        this.favorite = favorite
        notifyDataSetChanged()
    }

    class MyViewHolder(
        val binding: AdItem1Binding
    ) : RecyclerView.ViewHolder(binding.root)
}

