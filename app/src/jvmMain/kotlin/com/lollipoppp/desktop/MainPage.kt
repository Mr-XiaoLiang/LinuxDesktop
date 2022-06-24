package com.lollipoppp.desktop

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
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

enum class Page {
    Input,
    Write
}