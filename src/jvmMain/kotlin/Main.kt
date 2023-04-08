package io.github.afalabarce.compose.desktop

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.sun.tools.javac.Main
import io.github.afalabarce.compose.desktop.composables.features.main.composables.MainScreen
import io.github.afalabarce.compose.desktop.composables.features.main.viewmodels.MainViewModel
import io.github.afalabarce.compose.desktop.di.UiModuleInjection
import io.github.afalabarce.compose.desktop.utilities.stringResource
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

fun main() = application {
    startKoin {
        modules(UiModuleInjection.getKoinModules())
    }

    Window(
        state = WindowState(WindowPlacement.Maximized),
        icon = painterResource("mipmap/ic_launcher.png"),
        title = Locale.current.stringResource("app_name"),
        onCloseRequest = ::exitApplication
    ) {
        MainScreen()
    }
}
