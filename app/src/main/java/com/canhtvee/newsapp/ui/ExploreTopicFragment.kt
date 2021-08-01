package com.canhtvee.newsapp.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canhtvee.newsapp.R
import com.canhtvee.newsapp.adapters.HeadlineRecyclerViewAdapter
import com.canhtvee.newsapp.data.models.Article
import com.canhtvee.newsapp.utils.Resource
import com.canhtvee.newsapp.viewmodels.ExploreTopicViewModel
import com.canhtvee.newsapp.viewmodels.WebViewModel
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreTopicFragment: Fragment(R.layout.fragment_explore_topic) {

    @Inject
    lateinit var exploreTopicViewModel: ExploreTopicViewModel

    @Inject
    lateinit var webViewModel: WebViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
        val toolbarTitle = view.findViewById<TextView>(R.id.toolbarTitle)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.appToolbar)
        toolbar.navigationIcon = AppCompatResources.getDrawable(toolbar.context, R.drawable.ic_backward)
        toolbar.setNavigationOnClickListener {
            mainNavController.popBackStack()
        }
        toolbarTitle.text = exploreTopicViewModel.tag

        val recyclerView = view.findViewById<RecyclerView>(R.id.exploreTopicRecyclerView)
        exploreTopicViewModel.topicData.observe(viewLifecycleOwner,  { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(recyclerView.context, "Loading...", Toast.LENGTH_LONG).show()
                }

                is Resource.Error -> {
                    Toast.makeText(recyclerView.context, "Error", Toast.LENGTH_LONG).show()
                }

                is Resource.Success -> {
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
    }

    private fun onItemClick(article: Article){
        webViewModel.setViewData(article)
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
        mainNavController.navigate(R.id.action_global_to_webViewFragment)
    }

}