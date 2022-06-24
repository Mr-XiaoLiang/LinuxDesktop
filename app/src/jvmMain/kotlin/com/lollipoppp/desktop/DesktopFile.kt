package com.lollipoppp.desktop

import androidx.compose.runtime.Composable
import com.lollipoppp.desktop.PreferencesHelper.Companion.localPreferences

object DesktopFile {

    private const val FILE_SUFFIX = ".desktop"

    private val preferences = localPreferences()

    var fileDir by preferences.with(FileHelper.defaultDesktopPath)

    fun fixFileName(name: String): String {
        return if (name.endsWith(FILE_SUFFIX, true)) {
            name
        } else {
            name + FILE_SUFFIX
        }
    }

    fun check(
        info: Desktop
    ): Boolean {
//        if (info.fileDir().isEmpty()) {
//
//        }
        return true
    }

    fun createValue(info: Desktop): String {
        return StringBuilder()
            .addLine("[Desktop Entry]")
            .addLine("Name=", info.appName())
            .addLine("GenericName=", info.genericName().ifBlank { info.appName() })
            .addLine("Comment=", info.comment())
            .addLine("Version=", info.version())
            .addLine("StartupNotify=", info.startupNotify().toString().lowercase())
            .addLine("Terminal=", info.terminal().toString().lowercase())
            .addLine("Type=", info.appType().name)
            .addLine("Exec=", info.execPath())
            .addLine("Icon=", info.iconPath())
            .addLine("Categories=", getCategoriesValue(info.categories()))
            .toString()
    }

    fun getCategoriesValue(categories: List<Categories>): String {
        val builder = StringBuilder()
        categories.forEach {
            builder.append(it.name)
            builder.append(";")
        }
        return builder.toString()
    }

    private fun StringBuilder.addLine(vararg value: String): StringBuilder {
        value.forEach {
            append(it)
        }
        append("\n")
        return this
    }

    enum class AppType {
        /**
         * 应用程序
         */
        Application,

        /**
         * 链接
         */
        Link,

        /**
         * 文件夹
         */
        Directory
    }

    enum class Categories(val label: String) {
        /** 网络应用 */
        Network("网络应用"),

        /** 社交沟通 */
        Chat("社交沟通"),

        /** 音乐欣赏 */
        Audio("音乐欣赏"),

        /** 视频播放 */
        Video("视频播放"),

        /** 图形图像 */
        Graphics("图形图像"),

        /** 办公学习 */
        Office("办公学习"),

        /** 阅读翻译 */
        Translation("阅读翻译"),

        /** 编程开发 */
        Development("编程开发"),

        /** 系统管理 */
        Utility("系统管理")
    }
    class Desktop(
        val fileName: () -> String,
        val fileDir: () -> String,
        val appName: () -> String,
        val comment: () -> String,
        val genericName: () -> String,
        val iconPath: () -> String,
        val execPath: () -> String,
        val appType: () -> AppType,
        val categories: () -> List<Categories>,
        val version: () -> String,
        val startupNotify: () -> Boolean,
        val terminal: () -> Boolean,
    )

}