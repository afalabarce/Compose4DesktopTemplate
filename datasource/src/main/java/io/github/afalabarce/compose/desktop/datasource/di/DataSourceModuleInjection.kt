package io.github.afalabarce.compose.desktop.datasource.di

import io.github.afalabarce.compose.desktop.datasource.remote.engines.provideKtorClientFactory
import io.github.afalabarce.compose.desktop.models.interfaces.KoinModuleLoader
import org.koin.core.module.Module
import org.koin.dsl.module

object DataSourceModuleInjection: KoinModuleLoader {
    override fun getKoinModules(): List<Module> = listOf(
        module{
            single { provideKtorClientFactory() }
            // TODO add some dependencies (be careful with ordering!!)
        }
    )
}