package dev.onedive.autoClick.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LockClock
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.onedive.autoClick.impl.MouseClickerRepositoryImpl
import dev.onedive.autoClick.ui.theme.appTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


@Composable
@Preview
fun app() {

    appTheme {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { screen() }

    }

}

@Composable
fun screen() {

    var currentStateIsAutoClicked by remember { mutableStateOf(false) }

    var textFieldUserInput by remember { mutableStateOf("1000") }

    val repository  = MouseClickerRepositoryImpl()

    TextField(
        value = textFieldUserInput,
        onValueChange = { textFieldUserInput = it.replace(Regex("[^0-9]"), "") },
        label = { Text("Enter the desired delay in milliseconds") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        enabled = !currentStateIsAutoClicked,
        singleLine = true,
        supportingText = {
            if (textFieldUserInput.isBlank() || textFieldUserInput.toLong() <= 200) {
                Text("Delay must be more than 200 milliseconds")
            }
        },
        leadingIcon = {
            if (currentStateIsAutoClicked) {
                Icon(Icons.Outlined.LockClock,"Lock Time Icon")
            } else {
                Icon(Icons.Outlined.Timer,"Delay Time Icon")
            }
        }
    )

    Spacer(modifier = Modifier.height(10.dp))

    Button(
        onClick = { currentStateIsAutoClicked = !currentStateIsAutoClicked },
        enabled = textFieldUserInput.isNotBlank() && textFieldUserInput.toLong() > 200
    ) {
        Text(if (currentStateIsAutoClicked) "Status : On" else "Status : Off")
    }

    LaunchedEffect(currentStateIsAutoClicked) {
        delay(700)

        if (currentStateIsAutoClicked && !repository.currentTimeIsStart) {
            while (isActive) repository.startClicker(textFieldUserInput.toLong())
        } else {
            repository.stopClicker()
        }


    }

}


fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        resizable = false,
        title = "Auto Left Mouse Clicker",
        visible = true,
    ) { app() }

}
