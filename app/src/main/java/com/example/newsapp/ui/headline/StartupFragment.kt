package com.example.newsapp.ui.headline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.adapters.HeadlineRecyclerViewAdapter
import com.example.newsapp.R
import com.example.newsapp.adapters.HeadlineBindingAdapter
import com.example.newsapp.data.models.Article
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.SourcePlanning
import com.example.newsapp.viewmodels.HeadlineViewModel
import com.example.newsapp.viewmodels.WebViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.startupRecyclerView)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.startup_layout_swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            headlineViewModel.deleteHeadline(sourcePlanning.startup)
            headlineViewModel.fetchStartup()
        }
        headlineViewModel.fetchStartup()
        headlineViewModel.startupData.observe(viewLifecycleOwner, { resource ->
            HeadlineBindingAdapter(this, webViewModel)
                .bindHeadline(resource, recyclerView, swipeRefreshLayout)
        })
    }
}