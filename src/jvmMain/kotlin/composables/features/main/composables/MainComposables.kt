package io.github.afalabarce.compose.desktop.composables.features.main.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import io.github.afalabarce.compose.desktop.composables.features.main.viewmodels.MainViewModel
import org.koin.java.KoinJavaComponent.get

@Composable
@Preview
fun MainScreen(viewModel: MainViewModel = get(MainViewModel::class.java)) {
    var text by remember { mutableStateOf("Hello, World!") }
    val isFirstLoad by viewModel.isFirstAppExecution.collectAsState()

    MaterialTheme {
        Button(onClick = {
            text = "Hello, ${ if (isFirstLoad) "First" else ""} Desktop!"
            viewModel.setFirstLoad(false)
        }) {
            Text(text)
        }
    }
}