package com.example.newsapp.ui.headline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import com.example.newsapp.adapters.HeadlineBindingAdapterNew
import com.example.newsapp.utils.SourcePlanning
import com.example.newsapp.viewmodels.HeadlineViewModel
import com.example.newsapp.viewmodels.WebViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LifeFragment : Fragment(R.layout.fragment_life) {

    @Inject
    lateinit var headlineViewModel: HeadlineViewModel

    @Inject
    lateinit var sourcePlanning: SourcePlanning

    @Inject
    lateinit var webViewModel: WebViewModel

    @Inject
    lateinit var headlineBindingAdapter: HeadlineBindingAdapterNew

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shimmerViewContainer = view.findViewById<ShimmerFrameLayout>(R.id.life_shimmer_view_container)
        val recyclerView = view.findViewById<RecyclerView>(R.id.lifeRecyclerView)
        headlineBindingAdapter.setShimmerViewActive(recyclerView, shimmerViewContainer)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.life_layout_swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            headlineViewModel.deleteHeadline(
                headlineViewModel.refreshFlag._lifeFlag, sourcePlanning.lifeSources
            )
        }

        headlineViewModel.fetchLife()
        headlineViewModel.lifeData.observe(viewLifecycleOwner, { resource ->
            headlineBindingAdapter.bindHeadlineShimming(
                resource, recyclerView, swipeRefreshLayout, shimmerViewContainer, this
            )
        })

        headlineViewModel.refreshFlag.lifeFlag.observe(viewLifecycleOwner, { flag ->
            if (flag) {
                headlineViewModel.refreshFlag._lifeFlag.value = false
                headlineViewModel.fetchLife()
                headlineViewModel.lifeData.observe(viewLifecycleOwner, { resource ->
                })
            }
        })
    }
}