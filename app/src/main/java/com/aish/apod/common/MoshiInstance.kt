package com.aish.apod.common

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject

/**
 * Moshi instance to convert JSONObject into required data class
 * **/
object MoshiInstance {
    fun <T> getMoshiJson(input: Class<T>, data: JSONObject): T? {
        val moshi = getMoshiInstance()
        val adapter = moshi.adapter(input)
        return adapter.lenient().fromJson(data.toString())
    }

    /**
     * Get Moshi Instance
     */
    fun getMoshiInstance(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}
