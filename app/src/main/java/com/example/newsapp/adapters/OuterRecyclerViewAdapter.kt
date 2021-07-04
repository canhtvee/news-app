package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.models.Article
import com.example.newsapp.utils.LinePagerIndicatorDecoration
import com.example.newsapp.utils.SourcePlanning
import javax.inject.Inject

class  OuterRecyclerViewAdapter(
    private val data: List<Article>,
    private val outerItemClickHandler: (String) -> Unit,
    private val innerItemClickHandler: (Article) -> Unit
) : RecyclerView.Adapter<OuterRecyclerViewAdapter.ViewHolder>() {

    var imageBindingAdapter = ImageBindingAdapter()

    val activeTags = SourcePlanning().tagList.filter { tag -> data.count { it.content.equals(tag) } > 0 }

    class ViewHolder(view: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView1)
        val innerRecyclerView: RecyclerView = view.findViewById(R.id.innerRecyclerView)
        init {
            itemView.setOnClickListener {
                onItemClicked(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.outer_item_view, viewGroup, false)
        val viewHolder = ViewHolder(view) {
            outerItemClickHandler(activeTags[it])
        }
        val helper = PagerSnapHelper()
        helper.attachToRecyclerView(viewHolder.innerRecyclerView)
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = activeTags[position]
        val dataSet: List<Article> = data.filter { it.content.equals(activeTags[position]) }
        val subSet = if (dataSet.size > 6) dataSet.subList(0, 6) else dataSet
        bindInnerRecyclerView(viewHolder.innerRecyclerView, subSet)
    }

    override fun getItemCount() = activeTags.size

    private fun bindInnerRecyclerView(recyclerView: RecyclerView, subSet: List<Article>) {
        val adapter = InnerRecyclerViewAdapter(subSet, innerItemClickHandler)
        val decorator = LinePagerIndicatorDecoration()
        val viewPool = RecyclerView.RecycledViewPool()
        recyclerView.setHasFixedSize(true)
        recyclerView.setRecycledViewPool(viewPool)
        val layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            .apply { initialPrefetchItemCount = 4 }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(decorator)
    }
}
