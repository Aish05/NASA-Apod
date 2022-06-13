package com.aish.apod.extensions

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * sets image resource to fab
 */
@BindingAdapter("android:src")
fun FloatingActionButton.setImageViewResource(resource: Int) {
    setImageResource(resource)
}