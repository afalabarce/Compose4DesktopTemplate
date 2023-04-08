package io.github.afalabarce.compose.desktop.data.preferences

import io.github.afalabarce.compose.desktop.models.engines.DatabaseEngine
import io.github.afalabarce.compose.desktop.datasource.preferences.PreferencesStore
import kotlinx.coroutines.flow.Flow

internal class PreferencesRepositoryImpl(
    private val preferencesStore: PreferencesStore
): PreferencesRepository {
    override val databaseEngine: Flow<DatabaseEngine>
        get() = preferencesStore.databaseEngine
    override val firstLoad: Flow<Boolean>
        get() = preferencesStore.firstLoad

    override suspend fun setDatabaseEngine(engine: DatabaseEngine) {
        preferencesStore.setDatabaseEngine(engine)
    }

    override suspend fun setFirstLoad(isFirstLoad: Boolean) {
        preferencesStore.setFirstLoad(isFirstLoad)
    }
}