package com.example.peliculaspopulares.repositorio

import kotlinx.coroutines.flow.Flow


interface SessionRepository {
    suspend fun session() : Flow<SessionData>
    suspend fun saveSession(email: Boolean)
    suspend fun logout()
}

class UserPreferencesRepository(private val prefs: UserPreferences) : SessionRepository {

    override suspend fun session() : Flow<SessionData> {
        return prefs.sessionFlow
    }

    override suspend fun saveSession(email: Boolean) {
        val now = System.currentTimeMillis()
        prefs.saveSession(email, now)
    }

    override suspend fun logout() {
        prefs.clearSession()
    }


}



