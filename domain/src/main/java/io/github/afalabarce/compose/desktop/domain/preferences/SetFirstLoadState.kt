package io.github.afalabarce.compose.desktop.domain.preferences

import io.github.afalabarce.compose.desktop.data.preferences.PreferencesRepository

class SetFirstLoadState(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(isFirstLoad: Boolean){
        repository.setFirstLoad(isFirstLoad)
    }
}