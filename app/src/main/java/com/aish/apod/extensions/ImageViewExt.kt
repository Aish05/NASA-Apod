package com.aish.apod.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * loads an image from url into ImageView
 */
@BindingAdapter("image")
fun ImageView.loadImageUrlData(url: String?) {
    Glide.with(this.context).load(url)
        .into(this)
}

/**
 * Set drawable for image view programmatically
 */
@BindingAdapter("image")
fun ImageView.image(drawable: Int?) {
    drawable?.let {
        this.setImageResource(it)
    }
}