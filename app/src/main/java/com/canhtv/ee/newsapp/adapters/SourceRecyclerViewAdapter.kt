package com.canhtv.ee.newsapp.adapters

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ee.newsapp.R


class SourceRecyclerViewAdapter(
    private val sourceIcons: TypedArray,
    val onItemClick: (String, String) -> Unit
) : RecyclerView.Adapter<SourceRecyclerViewAdapter.ViewHolder>() {

    val sourceNames = listOf<String>(
        "Wired",
        "The Wall Street Journal",
        "The Verge",
        "The Next Web",
        "Tech Radar",
        "Tech Crunch",
        "Talk Sport",
        "Recode",
        "New Scientist",
        "Hacker News",
        "Fox Sports",
        "Fortune Magazine",
        "ENSP Mazagine",
        "Engadget",
        "Business Insider",
    )

    val sourceIds = listOf<String>(
        "wired",
        "the-wall-street-journal",
        "the-verge",
        "the-next-web",
        "techradar",
        "techcrunch",
        "talksport",
        "recode",
        "new-scientist",
        "hacker-news",
        "fox-sports",
        "fortune",
        "espn",
        "engadget",
        "business-insider",
    )

    class ViewHolder(view: View,
                     onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.grid_item_view_textView)
        val imageView: ImageView = view.findViewById(R.id.grid_item_view_imageView)
        init {
            itemView.setOnClickListener {
                onItemClicked(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.source_item_view, viewGroup, false)
        return ViewHolder(view) {
            onItemClick(sourceIds[it], sourceNames[it])
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = sourceNames[position]
        viewHolder.imageView.setImageDrawable(sourceIcons.getDrawable(position))
    }

    override fun getItemCount() = sourceNames.size
}
