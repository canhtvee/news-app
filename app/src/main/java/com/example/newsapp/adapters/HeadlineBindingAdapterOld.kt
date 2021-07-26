package com.example.newsapp.adapters

import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import com.example.newsapp.data.models.Article
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodels.WebViewModel
import com.facebook.shimmer.ShimmerFrameLayout

class HeadlineBindingAdapterOld (
    val fragment: Fragment,
    val webViewModel: WebViewModel
) {

    fun bindHeadline(
        resource: Resource<List<Article>>,
        recyclerView: RecyclerView,
        swipeRefreshLayout: SwipeRefreshLayout,
    ) {
        when (resource) {
            is Resource.Loading -> {
                Toast.makeText(recyclerView.context, "Loading...", Toast.LENGTH_SHORT).show()
            }

            is Resource.Error -> {
                Toast.makeText(recyclerView.context, "Error", Toast.LENGTH_SHORT).show()
            }

            is Resource.Success -> {
                swipeRefreshLayout.isRefreshing = false

                val itemDivider = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
                itemDivider.setDrawable(AppCompatResources.getDrawable(recyclerView.context, R.drawable.item_divider)!!)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        .apply { initialPrefetchItemCount = 6 }
                    adapter = HeadlineRecyclerViewAdapter(resource.data){
                        onItemClick(it)
                    }
                    hasFixedSize()
                    addItemDecoration(itemDivider)
                }
            }
        }
    }

    fun bindHeadlineShimming(
        resource: Resource<List<Article>>,
        recyclerView: RecyclerView,
        swipeRefreshLayout: SwipeRefreshLayout,
        shimmerViewContainer: ShimmerFrameLayout
    ) {
        when (resource) {
            is Resource.Loading -> {
                setShimmerViewActive(recyclerView, shimmerViewContainer)
            }

            is Resource.Error -> {
                Toast.makeText(recyclerView.context, "Error", Toast.LENGTH_SHORT).show()
            }

            is Resource.Success -> {

                if (resource.data.toString() == "[]") {
                    setShimmerViewActive(recyclerView, shimmerViewContainer)
                } else {
                    setRecyclerViewActive(recyclerView, shimmerViewContainer)
                }

                swipeRefreshLayout.isRefreshing = false

                val itemDivider = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
                itemDivider.setDrawable(AppCompatResources.getDrawable(recyclerView.context, R.drawable.item_divider)!!)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        .apply { initialPrefetchItemCount = 6 }
                    adapter = HeadlineRecyclerViewAdapter(resource.data){
                        onItemClick(it)
                    }
                    hasFixedSize()
                    addItemDecoration(itemDivider)
                }
            }
        }
    }

    private fun setShimmerViewActive(recyclerView: RecyclerView, shimmerViewContainer: ShimmerFrameLayout) {
        recyclerView.visibility = View.GONE
        shimmerViewContainer.visibility = View.VISIBLE
        shimmerViewContainer.startShimmerAnimation()
    }

    private fun setRecyclerViewActive(recyclerView: RecyclerView, shimmerViewContainer: ShimmerFrameLayout) {
        shimmerViewContainer.stopShimmerAnimation()
        shimmerViewContainer.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun onItemClick(article: Article){
        webViewModel.setViewData(article)
        val mainNavController = Navigation.findNavController(fragment.requireActivity(), R.id.nav_host_fragment_container)
        mainNavController.navigate(R.id.action_global_to_webViewFragment)
    }
}