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
class BusinessFragment : Fragment(R.layout.fragment_business) {

    @Inject
    lateinit var headlineViewModel: HeadlineViewModel

    @Inject
    lateinit var sourcePlanning: SourcePlanning

    @Inject
    lateinit var webViewModel: WebViewModel

    @Inject
    lateinit var headlineBindingAdapter: HeadlineBindingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shimmerViewContainer = view.findViewById<ShimmerFrameLayout>(R.id.business_shimmer_view_container)
        val recyclerView = view.findViewById<RecyclerView>(R.id.businessRecyclerView)
        headlineBindingAdapter.setShimmerViewActive(recyclerView, shimmerViewContainer)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.business_layout_swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            headlineViewModel.deleteHeadline(
                headlineViewModel.refreshFlag._businessFlag, sourcePlanning.businessSources
            )
        }

        headlineViewModel.fetchBusiness()
        headlineViewModel.businessData.observe(viewLifecycleOwner, { resource ->
            headlineBindingAdapter.bindHeadlineShimming(
                resource, recyclerView, swipeRefreshLayout, shimmerViewContainer, this
            )
        })

        headlineViewModel.refreshFlag.businessFlag.observe(viewLifecycleOwner) { flag ->
            if (flag) {
                headlineViewModel.refreshFlag._businessFlag.value = false
                headlineViewModel.fetchBusiness()
                headlineViewModel.businessData.observe(viewLifecycleOwner, {
                })
            }
        }
    }
}