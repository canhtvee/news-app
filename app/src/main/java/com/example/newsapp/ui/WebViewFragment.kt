package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import com.example.newsapp.R
import com.example.newsapp.viewmodels.WebViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        val progressBar = view.findViewById<ProgressBar>(R.id.webViewProgressBar)
        progressBar.progress = 0
        val webViewActionBar = view.findViewById<BottomNavigationView>(R.id.webViewActionBar)
        webViewActionBar.setOnItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.iconClear -> {
                    Toast.makeText(context, "icon clear", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.iconSave -> {
                    // Respond to navigation item 2 click
                    Toast.makeText(context, "icon save", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.iconShare -> {
                    Toast.makeText(context, "icon share", Toast.LENGTH_LONG).show()
                    true
                }
                else -> {
                    Toast.makeText(context, "icon refresh", Toast.LENGTH_LONG).show()
                    false
                }
            }
        }
        val webView = view.findViewById<WebView>(R.id.webView).apply {
            settings.javaScriptEnabled = true
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.INVISIBLE
            }
        }

        webViewModel.viewData.observe(viewLifecycleOwner, {
            webView.loadUrl(it.url)
        })

    }
}