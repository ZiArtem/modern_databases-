package com.example.modern_databases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.Favorite
import com.example.modern_databases.data.Image
import kotlinx.android.synthetic.main.ad_item_1.view.*
import java.text.SimpleDateFormat

class AdAdapter : RecyclerView.Adapter<AdAdapter.MyViewHolder>() {
    private var adList = emptyList<Ad>()
    private var image = emptyList<Image>()
    private var favorite = emptyList<Favorite>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
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
}

