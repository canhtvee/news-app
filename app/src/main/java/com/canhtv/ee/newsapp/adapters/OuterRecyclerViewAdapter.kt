package com.canhtv.ee.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import ee.newsapp.R
import com.canhtv.ee.newsapp.data.models.Article
import com.canhtv.ee.newsapp.utils.LinePagerIndicatorDecoration
import com.canhtv.ee.newsapp.utils.SourcePlanning
import com.google.android.material.appbar.MaterialToolbar

class  OuterRecyclerViewAdapter(
    private val data: List<Article>,
    private val outerItemClickHandler: (String) -> Unit,
    private val innerItemClickHandler: (Article) -> Unit
) : RecyclerView.Adapter<OuterRecyclerViewAdapter.ViewHolder>() {

    private val activeTags = SourcePlanning().tagList.filter { tag -> data.count { it.content.equals(tag) } > 0 }

    class ViewHolder(view: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val toolbar = view.findViewById<MaterialToolbar>(R.id.outerItemViewToolbar)
        val innerRecyclerView: RecyclerView = view.findViewById(R.id.innerRecyclerView)
        init {

            toolbar.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.iconForward) {
                    onItemClicked(absoluteAdapterPosition)
                }
                return@setOnMenuItemClickListener true
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
        viewHolder.toolbar.title = activeTags[position]
        val dataSet: List<Article> = data.filter { it.content.equals(activeTags[position]) }
        val subSet = if (dataSet.size > 7) dataSet.subList(0, 7) else dataSet
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
