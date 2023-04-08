package io.github.afalabarce.compose.desktop.domain.preferences

import io.github.afalabarce.compose.desktop.data.preferences.PreferencesRepository
import io.github.afalabarce.compose.desktop.models.engines.DatabaseEngine
import kotlinx.coroutines.flow.Flow

class GetDatabaseEngine(
    private val repository: PreferencesRepository
) {
    operator fun invoke(): Flow<DatabaseEngine> = repository.databaseEngine
}