package com.example.peliculaspopulares.repositorio

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


data class SessionData(
    val sesion: Boolean,
    val loginDate: Long
)

val Context.dataStore by preferencesDataStore(name = "settings")


class UserPreferences(private val context: Context) {

    private val SESION_KEY = stringPreferencesKey("sesion")
    private val LOGIN_DATE_KEY = longPreferencesKey("login_date")

    val sessionFlow: Flow<SessionData> = context.dataStore.data.map {
        pref ->
           SessionData(
               sesion = pref[SESION_KEY].toBoolean(),
               loginDate = pref[LOGIN_DATE_KEY] ?: 0L
           )

    }
    suspend fun saveSession(sesion: Boolean, loginDate: Long) {
        context.dataStore.edit { prefs ->
            prefs[SESION_KEY] = sesion.toString()
            prefs[LOGIN_DATE_KEY] = loginDate
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

}