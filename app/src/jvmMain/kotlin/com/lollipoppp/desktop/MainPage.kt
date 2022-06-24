package com.lollipoppp.desktop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lollipoppp.desktop.Page.Input
import com.lollipoppp.desktop.Page.Write
import com.lollipoppp.desktop.utils.DesktopFile

@Composable
fun App() {

    var pageIndex by remember { mutableStateOf(Input) }
    var desktopInfo: DesktopFile.Snapshot? = null

    MaterialTheme {
        when (pageIndex) {
            Input -> {
                InputPage(desktopInfo) {
                    desktopInfo = it
                    pageIndex = Write
                }
            }
            Write -> {
                val info = desktopInfo
                if (info == null) {
                    pageIndex = Input
                } else {
                    WriteFilePage(info) {
                        pageIndex = Input
                    }
                }
            }
        }
    }
}

@Composable
fun slideByAnimation(expand: Boolean, content: @Composable AnimatedVisibilityScope.() -> Unit) {
    AnimatedVisibility(
        visible = expand,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = Modifier.fillMaxSize()
    ) {
        content()
    }
}

enum class Page {
    Input,
    Write
}