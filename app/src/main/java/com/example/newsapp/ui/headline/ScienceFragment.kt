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
import com.example.newsapp.adapters.HeadlineRecyclerViewAdapter
import com.example.newsapp.R
import com.example.newsapp.adapters.HeadlineBindingAdapter
import com.example.newsapp.data.models.Article
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodels.HeadlineViewModel
import com.example.newsapp.viewmodels.WebViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScienceFragment : Fragment(R.layout.fragment_science) {

    @Inject
    lateinit var headlineViewModel: HeadlineViewModel

    @Inject
    lateinit var webViewModel: WebViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.scienceRecyclerView)
        headlineViewModel.fetchScience()
        headlineViewModel.scienceData.observe(viewLifecycleOwner, Observer { resource ->
            HeadlineBindingAdapter(this, webViewModel)
                .bindHeadline(resource, recyclerView)
        })
    }
}