package com.aish.apod.common

import androidx.lifecycle.Observer

/**
 *
 * Wrapper class for Observer that forces data to be non-null
 */
class SafeObserver<T : Any>(private val f: (T) -> Unit) : Observer<T> {

    override fun onChanged(t: T?) {
        f(t!!)
    }
}
