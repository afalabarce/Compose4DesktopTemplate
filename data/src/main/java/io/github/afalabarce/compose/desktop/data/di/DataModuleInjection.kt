package io.github.afalabarce.compose.desktop.data.di

import io.github.afalabarce.compose.desktop.data.preferences.PreferencesRepository
import io.github.afalabarce.compose.desktop.data.preferences.PreferencesRepositoryImpl
import io.github.afalabarce.compose.desktop.datasource.di.DataSourceModuleInjection
import io.github.afalabarce.compose.desktop.models.interfaces.KoinModuleLoader
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModuleInjection: KoinModuleLoader {
    override fun getKoinModules(): List<Module> =
        DataSourceModuleInjection.getKoinModules()
            .union(listOf(
                module {
                    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }

                    // TODO add some dependencies. Be careful with dependencies ordering
                }
            )
        ).toList()
}