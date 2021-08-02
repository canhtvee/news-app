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
class TechnologyFragment : Fragment(R.layout.fragment_technology) {

    @Inject
    lateinit var headlineViewModel: HeadlineViewModel

    @Inject
    lateinit var sourcePlanning: SourcePlanning

    @Inject
    lateinit var webViewModel: WebViewModel

    @Inject
    lateinit var headlineBindingAdapter: HeadlineBindingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shimmerViewContainer = view.findViewById<ShimmerFrameLayout>(R.id.tech_shimmer_view_container)
        val recyclerView = view.findViewById<RecyclerView>(R.id.techRecyclerView)
        headlineBindingAdapter.setShimmerViewActive(recyclerView, shimmerViewContainer)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.tech_layout_swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            headlineViewModel.deleteHeadline(
                headlineViewModel.refreshFlag._techFlag, sourcePlanning.techSources
            )
        }

        headlineViewModel.fetchTech()
        headlineViewModel.techData.observe(viewLifecycleOwner, { resource ->
            headlineBindingAdapter.bindHeadlineShimming(
                resource, recyclerView, swipeRefreshLayout, shimmerViewContainer, this
            )
        })

        headlineViewModel.refreshFlag.techFlag.observe(viewLifecycleOwner) { flag ->
            if (flag) {
                headlineViewModel.refreshFlag._techFlag.value = false
                headlineViewModel.fetchTech()
                headlineViewModel.techData.observe(viewLifecycleOwner, {
                })
            }
        }
    }
}