package com.canhtv.ee.newsapp.adapters

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import ee.newsapp.R

class ImageBindingAdapter {

    fun bindImage(context: Context, imageView: ImageView, imageUrl: String) {
        try {
            Glide.with(context)
                .load(imageUrl) // image url
                .placeholder(R.drawable.ic_image_loading) // any placeholder to load at start
                .error(R.drawable.ic_image_loading_error)  // any image in case of error
                .thumbnail(0.4f)
                .skipMemoryCache(true)
                .centerCrop()
                .into(imageView)
        } catch (e: Exception) {
            throw e
        }
    }
}