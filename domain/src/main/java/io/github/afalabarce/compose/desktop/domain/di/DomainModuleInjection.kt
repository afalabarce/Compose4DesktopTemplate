package io.github.afalabarce.compose.desktop.domain.di

import io.github.afalabarce.compose.desktop.data.di.DataModuleInjection
import io.github.afalabarce.compose.desktop.domain.preferences.GetDatabaseEngine
import io.github.afalabarce.compose.desktop.domain.preferences.GetIsFirstLoad
import io.github.afalabarce.compose.desktop.domain.preferences.SetDatabaseEngine
import io.github.afalabarce.compose.desktop.domain.preferences.SetFirstLoadState
import io.github.afalabarce.compose.desktop.models.interfaces.KoinModuleLoader
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModuleInjection: KoinModuleLoader {
    override fun getKoinModules(): List<Module> =
        DataModuleInjection.getKoinModules()
            .union(listOf(
                module {
                    factory { GetDatabaseEngine(get()) }
                    factory { GetIsFirstLoad(get()) }
                    factory { SetFirstLoadState(get()) }
                    factory { SetDatabaseEngine(get()) }
                }
            )
            ).toList()
}