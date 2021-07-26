package com.example.newsapp.ui.headline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import com.example.newsapp.adapters.HeadlineBindingAdapterOld
import com.example.newsapp.utils.SourcePlanning
import com.example.newsapp.viewmodels.HeadlineViewModel
import com.example.newsapp.viewmodels.WebViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.lifeRecyclerView)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.life_layout_swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            headlineViewModel.deleteHeadline(headlineViewModel.refreshFlag._lifeFlag, sourcePlanning.lifeSources)
        }
        headlineViewModel.fetchLife()
        headlineViewModel.lifeData.observe(viewLifecycleOwner, { resource ->
            HeadlineBindingAdapterOld(this, webViewModel)
                .bindHeadline(resource, recyclerView, swipeRefreshLayout)
        })
        headlineViewModel.refreshFlag.lifeFlag.observe(viewLifecycleOwner, { flag ->
            if (flag) {
                headlineViewModel.refreshFlag._lifeFlag.value = false
                headlineViewModel.fetchLife()
                headlineViewModel.lifeData.observe(viewLifecycleOwner, { resource ->
                    resource.toString() // can not be deleted
                })
            }
        })
    }
}