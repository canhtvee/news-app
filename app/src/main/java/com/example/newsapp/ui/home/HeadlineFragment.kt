package com.example.newsapp.ui.home

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.R
import com.example.newsapp.adapters.HeadlineFragmentAdapter
import com.example.newsapp.viewmodels.HeadlineViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HeadlineFragment : Fragment() {

    @Inject
    lateinit var headlineViewModel: HeadlineViewModel

    val title = listOf<String>("Business", "Technology", "Startup", "Science", "Life")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_headline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewPager = view.findViewById<ViewPager2>(R.id.pager)
        viewPager.adapter = HeadlineFragmentAdapter(this)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = title[position]
        }.attach()

    }
}