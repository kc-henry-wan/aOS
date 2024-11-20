package com.wellbeing.pharmacyjob.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.model.UserDoc

class ImageAdapter(
    private var images: List<UserDoc>, // ImageItem contains title and image URL or Bitmap
    private var onImageClick: (UserDoc) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageThumbnail: ImageView = view.findViewById(R.id.imageThumbnail)
        val textTitle: TextView = view.findViewById(R.id.textTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = images[position]

        holder.textTitle.text = item.imageType

//         // Load the configuration
//        val imageUrl =
//            ConfigReader.loadConfig(holder.itemView.context).getImageUrl() ?: "http://192.168.68.123:73/api/v1/mydoc/download/"
//        val imageUrl = "http://192.168.68.123:73/api/v1/mydoc/download/"+item.imageId
        val imageUrl =
            "https://s3.amazonaws.com/coursera_assets/meta_images/generated/CERTIFICATE_LANDING_PAGE/CERTIFICATE_LANDING_PAGE~TA9FQG4V38ZN/CERTIFICATE_LANDING_PAGE~TA9FQG4V38ZN.jpeg"

        holder.imageThumbnail.load(imageUrl) {
            size(200, 200)
            placeholder(R.drawable.ic_upload_placeholder) // Shown while loading
            error(R.drawable.ic_alpha_x_circle)       // Shown if loading fails
        }

        // Set click listener for full-screen preview
        holder.imageThumbnail.setOnClickListener {
            onImageClick(item)
        }
    }

    fun updateData(newItems: List<UserDoc>) {
        AppLogger.d("ImageAdapter", "updateData, size:" + newItems.size)
        images = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = images.size
}
