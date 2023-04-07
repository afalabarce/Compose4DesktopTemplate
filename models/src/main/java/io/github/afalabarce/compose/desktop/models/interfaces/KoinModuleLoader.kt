package io.github.afalabarce.compose.desktop.models.interfaces

import org.koin.core.module.Module

interface KoinModuleLoader {
    fun getKoinModules(): List<Module>
}