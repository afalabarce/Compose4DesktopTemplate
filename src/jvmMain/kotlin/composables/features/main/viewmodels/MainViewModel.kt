package io.github.afalabarce.compose.desktop.composables.features.main.viewmodels

import io.github.afalabarce.compose.desktop.domain.preferences.GetIsFirstLoad
import io.github.afalabarce.compose.desktop.domain.preferences.SetFirstLoadState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val isFirstLoad: GetIsFirstLoad,
    private val setFirstLoadState: SetFirstLoadState,
): CoroutineScope by CoroutineScope(Dispatchers.IO) {
    fun setFirstLoad(isFirstLoad: Boolean) {
        this.launch {
            setFirstLoadState(isFirstLoad)
        }
    }

    private val _isFirstAppExecution by lazy { MutableStateFlow(true) }
    val isFirstAppExecution: StateFlow<Boolean>
        get() = _isFirstAppExecution
    init {
        launch {
            isFirstLoad().collect { isFirstExecution ->
                    _isFirstAppExecution.update { isFirstExecution }

            }
        }
    }
}