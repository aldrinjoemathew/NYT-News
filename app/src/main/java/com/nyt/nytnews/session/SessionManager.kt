package com.nyt.nytnews.session

import android.content.SharedPreferences
import com.nyt.nytnews.db.models.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val json: Json
) {
    private val USER_SESSION = "USER_SESSION"

    var user: User?
        get() = getValue(USER_SESSION)
        set(user) = storeValue(USER_SESSION, user)

    fun logout() = clearSession()

    fun isLoggedIn() = user != null

    private fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }

    private inline fun <reified T : Any> getValue(key: String): T? {
        return try {
            val value = sharedPreferences.getString(key, null)
            json.decodeFromString<T>(value ?: return null)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private inline fun <reified T> storeValue(key: String, value: T?) {
        try {
            val valueText = json.encodeToString(value)
            sharedPreferences.edit().putString(key, valueText).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}