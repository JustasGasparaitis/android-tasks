package com.example.taskone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taskone.Constants.PHOTO_LIST_SPAN_COUNT
import com.example.taskone.databinding.RowPhotoBinding

class PhotoListAdapter : RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder>() {

    class PhotoListViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RowPhotoBinding.bind(view)
    }

    var items = listOf<String>()

    private var singleClickListener: ((String) -> Unit)? = null

    fun setOnClickListener(
        singleClickListener: ((String) -> Unit)? = null,
    ) {
        this.singleClickListener = singleClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_photo, parent, false)

        // Make grid height parameters match PHOTO_LIST_SPAN_COUNT
        // so that grid is PHOTO_LIST_SPAN_COUNT x PHOTO_LIST_SPAN_COUNT (e.g. 4x4)
        val params = view.layoutParams as GridLayoutManager.LayoutParams
        params.height = parent.measuredHeight / PHOTO_LIST_SPAN_COUNT
        view.layoutParams = params

        return PhotoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        // Load image into ImageView
        Glide.with(holder.itemView.context)
            .load(items[position])
            .into(holder.binding.imageView)

        // Set click listener to image view, if listener not null
        holder.binding.imageView.setOnClickListener {
            singleClickListener?.let { click ->
                click(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}