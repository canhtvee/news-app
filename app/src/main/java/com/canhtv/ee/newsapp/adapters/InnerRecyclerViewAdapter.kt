package com.canhtv.ee.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ee.newsapp.R
import com.canhtv.ee.newsapp.data.models.Article

class InnerRecyclerViewAdapter(
    private val dataSet: List<Article>,
    val itemClickHandler: (Article) -> Unit
) : RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder>() {

    private var imageBindingAdapter = ImageBindingAdapter()

    class ViewHolder(view: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.innerTextView)
        val imageView: ImageView = view.findViewById(R.id.innerImageView)
        init {
            itemView.setOnClickListener {
                onItemClicked(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.inner_item_view, viewGroup, false)
        return ViewHolder(view) {
            itemClickHandler(dataSet[it])
        }
    }

     override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].title
        imageBindingAdapter.bindImage(
            viewHolder.imageView.context,
            viewHolder.imageView,
            dataSet[position].urlToImage
        )
    }

    override fun getItemCount() = dataSet.size

}
