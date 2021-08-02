package com.canhtv.ee.newsapp.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ee.newsapp.R
import com.canhtv.ee.newsapp.viewmodels.WebViewModel
import com.google.android.material.appbar.MaterialToolbar
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
        val toolbar = view.findViewById<MaterialToolbar>(R.id.webViewToolbar)
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)

        val progressBar = view.findViewById<ProgressBar>(R.id.webViewProgressBar)
            .apply {
                progress = 0
                max = 100
            }
        val swipeToRefresh = view.findViewById<SwipeRefreshLayout>(R.id.webView_layout_swipe_to_refresh)
        val webView = view.findViewById<WebView>(R.id.webView).apply {
            settings.javaScriptEnabled = true
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress

                if (newProgress >= 70) {
                    progressBar.visibility = View.GONE
                    swipeToRefresh.isRefreshing = false

                }

            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.progress = 100
                progressBar.visibility = View.GONE
                swipeToRefresh.isRefreshing = false
            }
        }

        webViewModel.viewData.observe(viewLifecycleOwner, {
            webView.loadUrl(it.url)
        })

        swipeToRefresh.setOnRefreshListener {
            webView.reload()
        }

        webView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack()
                        return@setOnKeyListener true
                    }
                }
            }
            return@setOnKeyListener false
        }

        toolbar.setNavigationOnClickListener {
            mainNavController.popBackStack()
        }

        toolbar.setOnMenuItemClickListener { menu ->
            if (menu.itemId == R.id.webIconRefresh) {
                webView.reload()
            }
            return@setOnMenuItemClickListener true
        }
    }
}