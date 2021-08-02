package com.canhtv.ee.newsapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.canhtv.ee.newsapp.ui.headline.*

class HeadlineFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BusinessFragment()
            1 -> TechnologyFragment()
            2 -> StartupFragment()
            3 -> ScienceFragment()
            else -> LifeFragment()
        }
    }
}