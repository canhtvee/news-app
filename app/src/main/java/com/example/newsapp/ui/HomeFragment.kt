package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.newsapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navHostFragment = childFragmentManager.findFragmentById(R.id.home_nav_host_fragment_container) as NavHostFragment
        val localNavController = navHostFragment.findNavController()
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .apply { labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED }
        NavigationUI.setupWithNavController(bottomNav, localNavController)

    }
}