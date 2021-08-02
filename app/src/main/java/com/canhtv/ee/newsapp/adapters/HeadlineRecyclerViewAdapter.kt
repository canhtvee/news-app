package com.canhtv.ee.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ee.newsapp.R
import com.canhtv.ee.newsapp.data.models.Article

class  HeadlineRecyclerViewAdapter(
    private val data: List<Article>,
    val ItemClickHandler: (Article) -> Unit
) : RecyclerView.Adapter<HeadlineRecyclerViewAdapter.ViewHolder>() {

    private var imageBindingAdapter = ImageBindingAdapter()

    class ViewHolder(view: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.headline_item_view_textView)
        val imageView: ImageView = view.findViewById(R.id.headline_item_view_imageView)
        init {
            itemView.setOnClickListener {
                onItemClicked(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.headline_item_view, viewGroup, false)
        return ViewHolder(view) {
            ItemClickHandler(data[it])
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = data[position].title
        imageBindingAdapter.bindImage(
            viewHolder.imageView.context,
            viewHolder.imageView,
            data[position].urlToImage
        )
    }

    override fun getItemCount() = data.size

}
