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
class StartupFragment : Fragment(R.layout.fragment_startup) {

    @Inject
    lateinit var headlineViewModel: HeadlineViewModel

    @Inject
    lateinit var sourcePlanning: SourcePlanning

    @Inject
    lateinit var webViewModel: WebViewModel

    @Inject
    lateinit var headlineBindingAdapter: HeadlineBindingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shimmerViewContainer = view.findViewById<ShimmerFrameLayout>(R.id.startup_shimmer_view_container)
        val recyclerView = view.findViewById<RecyclerView>(R.id.startupRecyclerView)
        headlineBindingAdapter.setShimmerViewActive(recyclerView, shimmerViewContainer)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.startup_layout_swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            headlineViewModel.deleteHeadline(
                headlineViewModel.refreshFlag._startupFlag, sourcePlanning.startup
            )
        }

        headlineViewModel.fetchStartup()
        headlineViewModel.startupData.observe(viewLifecycleOwner, { resource ->
            headlineBindingAdapter.bindHeadlineShimming(
                resource, recyclerView, swipeRefreshLayout, shimmerViewContainer, this
            )
        })

        headlineViewModel.refreshFlag.startupFlag.observe(viewLifecycleOwner) { flag ->
            if (flag) {
                headlineViewModel.refreshFlag._startupFlag.value = false
                headlineViewModel.fetchStartup()
                headlineViewModel.startupData.observe(viewLifecycleOwner, {
                })
            }
        }
    }
}