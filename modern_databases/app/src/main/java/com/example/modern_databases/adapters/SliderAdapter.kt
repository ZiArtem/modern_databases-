package com.example.modern_databases.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.modern_databases.R
import com.example.modern_databases.data.entities.Image
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.slider_item.view.*

class SliderAdapter :
    SliderViewAdapter<SliderAdapter.Holder>() {
    private var imageList = emptyList<Image>()

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val inflater: View =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return Holder(inflater)
    }

    class Holder(itemView: View) : ViewHolder(itemView) {
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.slider_item.load(imageList[position].image) {
            transformations(RoundedCornersTransformation(40f))
        }
    }

    fun setData(ad_: List<Image>) {
        imageList = ad_
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return imageList.size
    }
}