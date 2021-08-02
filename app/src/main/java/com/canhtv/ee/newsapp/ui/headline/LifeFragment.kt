package com.canhtv.ee.newsapp.ui.headline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ee.newsapp.R
import com.canhtv.ee.newsapp.adapters.HeadlineBindingAdapter
import com.canhtv.ee.newsapp.utils.SourcePlanning
import com.canhtv.ee.newsapp.viewmodels.HeadlineViewModel
import com.canhtv.ee.newsapp.viewmodels.WebViewModel
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
    lateinit var headlineBindingAdapter: HeadlineBindingAdapter

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

        headlineViewModel.refreshFlag.lifeFlag.observe(viewLifecycleOwner) { flag ->
            if (flag) {
                headlineViewModel.refreshFlag._lifeFlag.value = false
                headlineViewModel.fetchLife()
                headlineViewModel.lifeData.observe(viewLifecycleOwner, {
                })
            }
        }
    }
}