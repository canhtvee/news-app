package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.HeadlineBindingAdapter
import com.example.newsapp.adapters.HeadlineRecyclerViewAdapter
import com.example.newsapp.data.models.Article
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodels.ExploreTopicViewModel
import com.example.newsapp.viewmodels.ExploreViewModel
import com.example.newsapp.viewmodels.WebViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreTopicFragment: Fragment(R.layout.fragment_explore_topic) {

    @Inject
    lateinit var exploreTopicViewModel: ExploreTopicViewModel

    @Inject
    lateinit var webViewModel: WebViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbarTitle = view.findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "NewsApp"

        val recyclerView = view.findViewById<RecyclerView>(R.id.exploreTopicRecyclerView)

        exploreTopicViewModel.topicData.observe(viewLifecycleOwner,  { resource ->
            HeadlineBindingAdapter(this, webViewModel)
                .bindHeadline(resource, recyclerView)
        })
    }
}