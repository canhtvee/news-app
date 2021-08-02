package com.canhtv.ee.newsapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import ee.newsapp.R
import com.canhtv.ee.newsapp.adapters.HeadlineFragmentAdapter
import com.canhtv.ee.newsapp.viewmodels.HeadlineViewModel
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
        val toolbarTitle = view.findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = resources.getString(R.string.headline)

        val viewPager = view.findViewById<ViewPager2>(R.id.pager)
        viewPager.adapter = HeadlineFragmentAdapter(this)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = title[position]
        }.attach()

    }
}