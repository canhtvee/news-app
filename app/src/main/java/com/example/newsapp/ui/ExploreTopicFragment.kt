package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.HeadlineRecyclerViewAdapter
import com.example.newsapp.data.models.Article
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodels.ExploreViewModel
import com.example.newsapp.viewmodels.WebViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreTopicFragment: Fragment(R.layout.fragment_explore_topic) {

    @Inject
    lateinit var exploreViewModel: ExploreViewModel

    @Inject
    lateinit var webViewModel: WebViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.exploreTopicRecyclerView)
        exploreViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(view.context, "Loading...", Toast.LENGTH_LONG).show()
                }

                is Resource.Error -> {
                    Toast.makeText(view.context, "Error", Toast.LENGTH_LONG).show()
                }

                is Resource.Success -> {
                    val tag = exploreViewModel.selectedTag
                    val topicData = resource.data.filter { it.content.equals(tag) }

                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            .apply { initialPrefetchItemCount = 4 }
                        adapter = HeadlineRecyclerViewAdapter(topicData){
                            itemClick(it)
                        }
                        hasFixedSize()
                        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                    }
                }
            }
        })
    }

    private fun itemClick(article: Article){
        webViewModel.setViewData(article)
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
        mainNavController.navigate(R.id.action_global_to_webViewFragment)
    }
}