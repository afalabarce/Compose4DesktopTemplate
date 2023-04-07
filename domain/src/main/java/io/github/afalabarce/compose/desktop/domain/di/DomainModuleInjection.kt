package io.github.afalabarce.compose.desktop.domain.di

import io.github.afalabarce.compose.desktop.data.di.DataModuleInjection
import io.github.afalabarce.compose.desktop.models.interfaces.KoinModuleLoader
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModuleInjection: KoinModuleLoader {
    override fun getKoinModules(): List<Module> =
        DataModuleInjection.getKoinModules()
            .union(listOf(
                module {
                    // TODO add some dependencies. Be careful with dependencies ordering
                }
            )
            ).toList()
}