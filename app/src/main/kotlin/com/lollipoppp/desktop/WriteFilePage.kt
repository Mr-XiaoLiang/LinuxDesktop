package com.lollipoppp.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lollipoppp.desktop.utils.DesktopFile
import com.lollipoppp.desktop.utils.DesktopWrite

@Composable
fun WriteFilePage(desktop: DesktopFile.Snapshot, back: () -> Unit) {

    val outputMessage = remember { mutableStateListOf<OutputMessage>() }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray).padding(8.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Box(modifier = Modifier.align(Alignment.Start).size(48.dp).clickable { back() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.Center).size(24.dp),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = Color.White,
            ) {
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    outputMessage.forEach {
                        Message(it)
                    }
                }
            }
        }
    }

    DesktopWrite.write(desktop) {
        onLog {
            outputMessage.add(OutputMessage(false, it))
        }
        onError {
            outputMessage.add(OutputMessage(true, it))
        }
    }

}

@Composable
fun Message(msg: OutputMessage) {
    Text(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp),
        style = TextStyle(
            color = if (msg.isError) {
                Color.Red
            } else {
                Color.Black
            },
            textAlign = TextAlign.Start
        ),
        text = msg.value
    )
}

class OutputMessage(val isError: Boolean, val value: String)