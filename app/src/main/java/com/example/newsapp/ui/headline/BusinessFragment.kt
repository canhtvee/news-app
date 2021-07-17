package com.example.newsapp.ui.headline

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import com.example.newsapp.adapters.HeadlineBindingAdapter
import com.example.newsapp.utils.SourcePlanning
import com.example.newsapp.viewmodels.HeadlineViewModel
import com.example.newsapp.viewmodels.WebViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        Log.d("BusinessFragment", "onCreateView")
        val recyclerView = view.findViewById<RecyclerView>(R.id.businessRecyclerView)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.business_layout_swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            val deleteFlag = headlineViewModel.deleteHeadline(sourcePlanning.businessSources)
            if (deleteFlag) {
                headlineViewModel.fetchBusiness()
            }
        }
        headlineViewModel.fetchBusiness()
        headlineViewModel.businessData.observe(viewLifecycleOwner, { resource ->
            HeadlineBindingAdapter(this, webViewModel)
                .bindHeadline(resource, recyclerView, swipeRefreshLayout)
        })

        view.findViewById<FloatingActionButton>(R.id.business_fab).setOnClickListener {
            Toast.makeText(context, "refresh news", Toast.LENGTH_SHORT).show()
            headlineViewModel.fetchBusiness()
            headlineViewModel.businessData.observe(viewLifecycleOwner, { resource ->
                Log.d("BusinessFragment", "businessData + ${resource.toString()}")

            })
        }
    }
}