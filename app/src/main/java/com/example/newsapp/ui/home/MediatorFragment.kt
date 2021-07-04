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
class MediatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_mediator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainNavController = Navigation.findNavController(requireActivity(),
            R.id.nav_host_fragment_container
        )
        val meditatorFragmentButton = view.findViewById<Button>(R.id.meditatorFragmentButton)
        meditatorFragmentButton.setOnClickListener {
            mainNavController.navigate(R.id.action_global_to_webViewFragment)
        }
    }
}