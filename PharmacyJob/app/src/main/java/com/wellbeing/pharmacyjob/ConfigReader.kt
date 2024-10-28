package com.wellbeing.pharmacyjob

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.util.Properties

object ConfigReader {
    private const val CONFIG_FILE = "config.properties"
    private const val KEY_BASE_URL = "BASE_URL"
    private var properties: Properties? = null

    fun loadConfig(context: Context) {
        properties = Properties()
        try {
            val inputStream: InputStream = context.assets.open(CONFIG_FILE)
            properties?.load(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getBaseUrl(): String? {
        return properties?.getProperty(KEY_BASE_URL)
    }
}
