package io.github.afalabarce.compose.desktop.datasource.preferences

import io.github.afalabarce.compose.desktop.models.engines.DatabaseEngine
import kotlinx.coroutines.flow.Flow

interface PreferencesStore {
    val databaseEngine: Flow<DatabaseEngine>
    val firstLoad: Flow<Boolean>

    suspend fun setDatabaseEngine(engine: DatabaseEngine)
    suspend fun setFirstLoad(isFirstLoad: Boolean)
}