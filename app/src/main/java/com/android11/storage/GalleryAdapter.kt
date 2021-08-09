package com.android11.storage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GalleryAdapter(private val context: Context, private val imageList: List<Image>,
                     private val checkedImages: MutableMap<String, Image>, private val imageSize: Int,
                     private val pickFiles: Boolean)
    : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val checkedView: ImageView = view.findViewById(R.id.checkedView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.gallery_image_item, parent, false)
        val holder = ViewHolder(view)
        if (pickFiles) {
            holder.itemView.setOnClickListener {
                val position = holder.adapterPosition
                val image = imageList[position]
                image.checked = !image.checked
                if (image.checked) {
                    checkedImages[image.uri.toString()] = image
                } else {
                    checkedImages.remove(image.uri.toString())
                }
                notifyItemChanged(position)
            }
        }
        return holder
    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.layoutParams.width = imageSize
        holder.imageView.layoutParams.height = imageSize
        val image = imageList[position]
        if (image.checked) {
            holder.checkedView.visibility = View.VISIBLE
        } else {
            holder.checkedView.visibility = View.INVISIBLE
        }
        val options = RequestOptions().placeholder(R.drawable.album_loading_bg).override(imageSize, imageSize)
        Glide.with(context).load(image.uri).apply(options).into(holder.imageView)
    }
}