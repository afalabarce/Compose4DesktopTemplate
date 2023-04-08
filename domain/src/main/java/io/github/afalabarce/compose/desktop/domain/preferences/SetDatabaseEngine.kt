package io.github.afalabarce.compose.desktop.domain.preferences

import io.github.afalabarce.compose.desktop.data.preferences.PreferencesRepository
import io.github.afalabarce.compose.desktop.models.engines.DatabaseEngine

class SetDatabaseEngine(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(databaseEngine: DatabaseEngine){
        repository.setDatabaseEngine(databaseEngine)
    }
}