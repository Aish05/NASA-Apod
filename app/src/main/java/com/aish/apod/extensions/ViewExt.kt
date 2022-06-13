package com.aish.apod.extensions

import android.view.MotionEvent
import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val DEFAULT_THROTTLE_DELAY = 300L


/**
 *
 * Wrapper method for `view.visibility = View.VISIBLE / View.GONE*
 * @param visible - true/false to show/hide a view
 * @param notVisibleType - default `gone` state - View.GONE or View.INVISIBLE;
 *                         null is used as a default, because it reflects the value passed by the
 *                         data binding code generation for optional parameters.
 */
@BindingAdapter(requireAll = false, value = ["setVisible", "notVisibleType"])
fun View.setVisible(visible: Boolean, notVisibleType: Int? = null) {
    visibility = if (visible) View.VISIBLE else (notVisibleType ?: View.GONE)
}

/**
 *
 * Adds a debounce to the standard click listener to avoid double pressing
 * @param listener - Standard click listener callback
 * @param delay - Time to wait between clicks.
 *                null is used as a default, because it reflects the value passed by the
 *                data binding code generation for optional parameters.
 */
@BindingAdapter(requireAll = false, value = ["throttledOnClick", "throttleDelay", "dataOnClicked"])
fun View.throttledOnClick(listener: View.OnClickListener, delay: Long? = null, data: Any? = null) {
    var lastClickTime = 0L
    var downTouch = false

    setOnClickListener { view ->
        if (System.currentTimeMillis() > lastClickTime + (delay ?: DEFAULT_THROTTLE_DELAY)) {
            listener.onClick(view)

            lastClickTime = System.currentTimeMillis()
        }
    }

    setOnTouchListener { v, event ->
        // Listening for the down and up touch events
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downTouch = true
                true
            }

            MotionEvent.ACTION_UP -> {
                if (downTouch) {
                    downTouch = false
                    performClick()
                    true
                } else {
                    false
                }
            }

            else -> false
        }
    }
}

/**
 *
 * Adds a debounce to the standard click listener to avoid double pressing*
 * @param listener - Click listener callback
 */
fun View.throttledOnClick(listener: () -> Unit) =
    throttledOnClick(View.OnClickListener { listener.invoke() })


