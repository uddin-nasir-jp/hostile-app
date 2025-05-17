package com.nasdin.hostile1.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nasdin.hostile1.model.KeplerData

class KeplerViewModel(application: Application) : AndroidViewModel(application) {
    val keplerData: List<KeplerData> = loadKeplerData(application)

    fun loadKeplerData(context: Context): List<KeplerData> {
        val jsonString = context.assets.open("kepler_data.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<KeplerData>>() {}.type
        return Gson().fromJson(jsonString, type)
    }
}