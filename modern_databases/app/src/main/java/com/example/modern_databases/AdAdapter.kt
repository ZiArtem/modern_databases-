package com.example.modern_databases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.data.Ad
import com.example.modern_databases.data.Image
import kotlinx.android.synthetic.main.ad_item_1.view.*
import java.text.SimpleDateFormat

class AdAdapter : RecyclerView.Adapter<AdAdapter.MyViewHolder>() {
    private var adList = emptyList<Ad>()
    private var image = emptyList<Image>()

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
//        val curimage = image[1]
        holder.itemView.title.text = currentItem.title.toString()
        holder.itemView.price.text = currentItem.price.toString() + " руб."
//        holder.itemView.date.text  =  sdf.format(currentItem.date)
//        holder.itemView.imageView3.load(curimage.image)
        holder.itemView.imageView3.load("https://vplate.ru/images/article/orig/2019/04/siba-inu-opisanie-porody-harakter-i-soderzhanie.jpg") {
            transformations(RoundedCornersTransformation(40f))
        }
    }

    fun setData(ad: List<Ad>) {
        this.adList = ad

        notifyDataSetChanged()
    }

    fun setImage(image: List<Image>) {
        this.image = image
        notifyDataSetChanged()
    }
}