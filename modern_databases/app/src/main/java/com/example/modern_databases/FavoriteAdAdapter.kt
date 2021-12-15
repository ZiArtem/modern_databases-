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
import kotlinx.android.synthetic.main.ad_item_1.view.imageView3
import kotlinx.android.synthetic.main.ad_item_1.view.price
import kotlinx.android.synthetic.main.ad_item_1.view.title
import kotlinx.android.synthetic.main.ad_item_favorite.view.*
import java.text.SimpleDateFormat

class FavoriteAdAdapter : RecyclerView.Adapter<FavoriteAdAdapter.MyViewHolder>() {
    private var adList = emptyList<Ad>()
    private var image = emptyList<Image>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ad_item_favorite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteAdAdapter.MyViewHolder, position: Int) {
        val sdf = SimpleDateFormat("dd.MM HH:mm")
        val currentItem = adList[position]
//        val curimage = image[1]
        holder.itemView.title.text = currentItem.title.toString()
        holder.itemView.price.text = currentItem.price.toString() + " руб."
        holder.itemView.description.text= currentItem.description.toString()
//        holder.itemView.date.text  =  sdf.format(currentItem.date)
//        holder.itemView.imageView3.load(curimage.image)
        var id_ad: Int = currentItem.id_ad

        var i: Int = 0
        for (item in image) {
            if (item.id_ad_ == id_ad) {
                holder.itemView.imageView3.load(item.image) {
                    transformations(RoundedCornersTransformation(40f))
                }
                break
            }
            i += 1
        }
        if (i == image.size) {
            holder.itemView.imageView3.load("https://pbs.twimg.com/media/E00ZZrKXoAEiyla.jpg") {
                transformations(RoundedCornersTransformation(40f))
            }
        }
    }

    override fun getItemCount(): Int {
        return adList.size
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