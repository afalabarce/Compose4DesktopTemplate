package io.github.afalabarce.compose.desktop.domain.preferences

import io.github.afalabarce.compose.desktop.data.preferences.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetIsFirstLoad(
    private val repository: PreferencesRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.firstLoad
}