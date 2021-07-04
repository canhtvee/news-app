package com.example.newsapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.newsapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btn1 = view.findViewById<Button>(R.id.followingFragmentButton)
        val mainNavController = Navigation.findNavController(requireActivity(),
            R.id.nav_host_fragment_container
        )
        btn1.setOnClickListener {
            mainNavController.navigate(R.id.action_global_to_webViewFragment)
        }
    }
}