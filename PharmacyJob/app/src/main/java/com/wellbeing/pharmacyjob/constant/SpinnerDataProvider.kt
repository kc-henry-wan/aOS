package com.wellbeing.pharmacyjob.constant

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Data class for holding key-value pairs
data class SpinnerItem(val key: String, val value: String)

// A separate class to handle the spinner data loading
class SpinnerDataProvider {

    companion object {
        // Static constant containing the JSON string
        private const val SPINNER_ITEMS_JSON = """
        [
            {"key": "DA", "value": "Sort By Date"},
            {"key": "DI", "value": "Sort By Distance"},
            {"key": "HR", "value": "Sort By Hourly Rate"},
            {"key": "TP", "value": "Sort By Total Paid"}
        ]
        """

        // Function to parse and return the list of SpinnerItem from JSON
        fun getSpinnerItems(): List<SpinnerItem> {
            val itemType = object : TypeToken<List<SpinnerItem>>() {}.type
            return Gson().fromJson(SPINNER_ITEMS_JSON, itemType)
        }
    }
}
