package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.newsapp.R
import com.example.newsapp.viewmodels.WebViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    @Inject
    lateinit var webViewModel: WebViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val webView = view.findViewById<WebView>(R.id.webView).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
        }

        webViewModel.viewData.observe(viewLifecycleOwner, Observer {
            webView.loadUrl(it.url)
        })
    }
}