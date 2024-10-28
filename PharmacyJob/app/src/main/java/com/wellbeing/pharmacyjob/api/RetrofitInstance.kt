package com.wellbeing.pharmacyjob.api

import android.content.Context
import com.wellbeing.pharmacyjob.ConfigReader
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val client = OkHttpClient.Builder().build()
    private lateinit var retrofit: Retrofit
    lateinit var api: ApiService

    fun init(context: Context) {
        ConfigReader.loadConfig(context) // Load the configuration
        val baseUrl = ConfigReader.getBaseUrl() ?: "http://ReadFileError.com" // Fallback if not found

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)
    }

}
