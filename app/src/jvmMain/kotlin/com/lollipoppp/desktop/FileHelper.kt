package com.lollipoppp.desktop

import java.io.File
import javax.swing.filechooser.FileSystemView

object FileHelper {

    val homeDir: File by lazy {
        FileSystemView.getFileSystemView().homeDirectory
    }

    val defaultDesktopPath: String by lazy {
        File(homeDir, "/.local/share/applications").absolutePath
    }

}