package com.canhtv.ee.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ee.newsapp.R
import com.canhtv.ee.newsapp.adapters.OuterRecyclerViewAdapter
import com.canhtv.ee.newsapp.data.models.Article
import com.canhtv.ee.newsapp.utils.Resource
import com.canhtv.ee.newsapp.viewmodels.ExploreTopicViewModel
import com.canhtv.ee.newsapp.viewmodels.ExploreViewModel
import com.canhtv.ee.newsapp.viewmodels.WebViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    @Inject
    lateinit var exploreViewModel: ExploreViewModel

    @Inject
    lateinit var exploreTopicViewModel: ExploreTopicViewModel

    @Inject
    lateinit var webViewModel: WebViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbarTitle = view.findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = resources.getString(R.string.explore)

        val shimmerViewContainer = view.findViewById<ShimmerFrameLayout>(R.id.explore_shimmer_view_container)
        val recyclerView = view.findViewById<RecyclerView>(R.id.exploreRecyclerView)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.explore_layout_swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            exploreViewModel.deleteExplore()
        }

        exploreViewModel.refreshFlag.observe(viewLifecycleOwner, { flag ->
            if (flag) {
                exploreViewModel._deleteFlag.value = false
                exploreViewModel.fetchExplore()
                exploreViewModel.exploreData.observe(viewLifecycleOwner, {
                })
            }
        })

        exploreViewModel.fetchExplore()
        exploreViewModel.exploreData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    setShimmerViewActive(recyclerView, shimmerViewContainer)
                }

                is Resource.Error -> {
                    Toast.makeText(view.context, "Error", Toast.LENGTH_SHORT).show()
                    setRecyclerViewActive(recyclerView, shimmerViewContainer)
                }

                is Resource.Success -> {
                    swipeRefreshLayout.isRefreshing = false

                    if (resource.data.toString() == "[]") {
                        setShimmerViewActive(recyclerView, shimmerViewContainer)
                    } else {
                        setRecyclerViewActive(recyclerView, shimmerViewContainer)
                    }

                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            .apply { initialPrefetchItemCount = 4 }
                        adapter = OuterRecyclerViewAdapter(
                            resource.data,
                            { onOuterItemClick(it) },
                            { onInnerItemClick(it) }
                        )
                        hasFixedSize()
                    }
                }
            }
        })
    }

    private fun onOuterItemClick(tag: String){
        val toolbarTitle =  when (tag) {
            "apple" -> "Apple News"
            "android" -> "Android News"
            "huawei" -> "Huawei News"
            "tesla" -> "Tesla News"
            "bitcoin" -> "Bitcoin News"
            "facebook" -> "Facebook News"
            else -> "Twitter News"
        }
        exploreTopicViewModel.tag = toolbarTitle
        exploreTopicViewModel.fetTagHeadline(tag)
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
        mainNavController.navigate(R.id.action_global_to_exploreTopicFragment)
    }
    private fun onInnerItemClick(article: Article){
        webViewModel.setViewData(article)
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
        mainNavController.navigate(R.id.action_global_to_webViewFragment)
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

}