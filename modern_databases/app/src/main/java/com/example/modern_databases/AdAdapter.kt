package com.example.modern_databases

import android.content.Context
import android.content.Intent
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.ListFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.RoomDatabase
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.Favorite
import com.example.modern_databases.data.Image
import com.example.modern_databases.data.UserViewModel
import kotlinx.android.synthetic.main.ad_item_1.view.*
import java.text.SimpleDateFormat
import com.like.LikeButton

import com.like.OnLikeListener
import java.lang.reflect.Array


class AdAdapter : RecyclerView.Adapter<AdAdapter.MyViewHolder>() {
    private var adList = emptyList<Ad>()
    private var image = emptyList<Image>()
    private lateinit var favorite: ArrayList<Favorite>
    private lateinit var context:Context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.getContext()
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ad_item_1, parent, false)
        )
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

        var i:Int =0
        for (item in image) {
            if (item.id_ad_==currentItem.id_ad )  {
                holder.itemView.imageView3.load(item.image){
                    transformations(RoundedCornersTransformation(40f))
                }
                break
            }
            i+=1
        }

        if (i == image.size) {
            holder.itemView.imageView3.load("https://ebar.co.za/wp-content/uploads/2018/01/menu-pattern-1-1.png") {
                transformations(RoundedCornersTransformation(40f))
            }
        }
        i=0
        for (j in favorite) {
            if (currentItem.id_ad == j.id_ad_) {
                holder.itemView.like_button.setLiked(true)
                break
            }
            i++
        }
        if (i==favorite.size)
            holder.itemView.like_button.setLiked(false)


        holder.itemView.imageView3.setOnClickListener {
            fun onClick(view:View) {
//                val intent = Intent(context, Favorite::class.java)
//                context.startActivity(intent)
//                overridePendingTransition(0, 0);
//                activity.startActivity(Intent(activity, NVirementEmmeteur::class.java))

            }
        }

        holder.itemView.like_button.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                Toast.makeText(
                    context ,
                    "press",
                    Toast.LENGTH_SHORT
                ).show()
                favorite.add(Favorite(0,adList[position].id_ad,1))
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

//    fun setFavorite(favorite: List<Favorite>) {
//        for (i in favorite)
//         this.favorite.add(i)
//        notifyDataSetChanged()
//    }
}

