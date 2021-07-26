package com.example.newsapp.ui.headline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import com.example.newsapp.adapters.HeadlineBindingAdapter
import com.example.newsapp.adapters.HeadlineRecyclerViewAdapter
import com.example.newsapp.data.models.Article
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.SourcePlanning
import com.example.newsapp.viewmodels.HeadlineViewModel
import com.example.newsapp.viewmodels.WebViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shimmerViewContainer = view.findViewById<ShimmerFrameLayout>(R.id.business_shimmer_view_container)
        val recyclerView = view.findViewById<RecyclerView>(R.id.businessRecyclerView)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.business_layout_swipe_to_refresh)
        setShimmerViewActive(recyclerView, shimmerViewContainer)

        swipeRefreshLayout.setOnRefreshListener {
            setShimmerViewActive(recyclerView, shimmerViewContainer)
            swipeRefreshLayout.isRefreshing = false
            headlineViewModel.deleteHeadline(headlineViewModel.refreshFlag._businessFlag, sourcePlanning.businessSources)
        }

        headlineViewModel.fetchBusiness()
        headlineViewModel.businessData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    setShimmerViewActive(recyclerView, shimmerViewContainer)
                }

                is Resource.Error -> {
                    Toast.makeText(recyclerView.context, "Error", Toast.LENGTH_SHORT).show()
                }

                is Resource.Success -> {
                    swipeRefreshLayout.isRefreshing = false
                    setRecyclerViewActive(recyclerView, shimmerViewContainer)
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
        })

        headlineViewModel.refreshFlag.businessFlag.observe(viewLifecycleOwner, { flag ->
            if (flag) {
                headlineViewModel.refreshFlag._businessFlag.value = false
                headlineViewModel.fetchBusiness()
                headlineViewModel.businessData.observe(viewLifecycleOwner, {
                })
            }
        })
    }

    private fun setShimmerViewActive(recyclerView: RecyclerView, shimmerViewContainer: ShimmerFrameLayout) {
        recyclerView.visibility = View.GONE
        shimmerViewContainer.startShimmerAnimation()
        shimmerViewContainer.visibility = View.VISIBLE
        Toast.makeText(recyclerView.context, "set shimmerView active done", Toast.LENGTH_SHORT).show()
    }

    private fun setRecyclerViewActive(recyclerView: RecyclerView, shimmerViewContainer: ShimmerFrameLayout) {
        recyclerView.visibility = View.VISIBLE
        shimmerViewContainer.stopShimmerAnimation()
        shimmerViewContainer.visibility = View.GONE
        Toast.makeText(recyclerView.context, "set recyclerView active done", Toast.LENGTH_SHORT).show()
    }

    private fun onItemClick(article: Article){
        webViewModel.setViewData(article)
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
        mainNavController.navigate(R.id.action_global_to_webViewFragment)
    }
}