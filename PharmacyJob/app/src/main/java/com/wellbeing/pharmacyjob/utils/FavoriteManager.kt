package com.wellbeing.pharmacyjob.utils

import android.content.Context
import android.content.SharedPreferences

class FavoriteManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val FAVORITES_KEY = "myfavorite_ids"
    }

    // Add an item ID to favorites
    fun addFavorite(id: String) {
        val favorites = getFavorites().toMutableSet()
        favorites.add(id)
        sharedPreferences.edit()
            .putStringSet(FAVORITES_KEY, favorites)
            .apply()
    }

    // Remove an item ID from favorites
    fun removeFavorite(id: String) {
        val favorites = getFavorites().toMutableSet()
        if (favorites.contains(id)) {
            favorites.remove(id)
            sharedPreferences.edit()
                .putStringSet(FAVORITES_KEY, favorites)
                .apply()
        }
    }

    // Get the list of favorite item IDs
    fun getFavorites(): Set<String> {
        return sharedPreferences.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    // Check if an item is in favorites
    fun isFavorite(id: String): Boolean {
        return getFavorites().contains(id)
    }
}
