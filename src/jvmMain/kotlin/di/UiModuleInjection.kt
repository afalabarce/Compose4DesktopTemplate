package io.github.afalabarce.compose.desktop.di

import io.github.afalabarce.compose.desktop.domain.di.DomainModuleInjection
import io.github.afalabarce.compose.desktop.models.interfaces.KoinModuleLoader
import org.koin.core.module.Module
import org.koin.dsl.module

object UiModuleInjection: KoinModuleLoader {
    override fun getKoinModules(): List<Module> =
        DomainModuleInjection.getKoinModules()
            .union(listOf(
                module {
                    // TODO add some dependencies. Be careful with dependencies ordering
                }
            )
            ).toList()
}