package com.aish.apod.extensions

/**
 * Validates the resource type is of video.
 */
fun String?.isVideoResource() = this?.containsIgnoreCase("video")

/**
 * Method to check string present or not ignoring case
 */
fun String.containsIgnoreCase(str: String) = this.contains(str, true)