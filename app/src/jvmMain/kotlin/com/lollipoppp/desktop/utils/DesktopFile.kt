package com.lollipoppp.desktop.utils

import com.lollipoppp.desktop.utils.PreferencesHelper.Companion.localPreferences

class DesktopFile(
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
) {

    companion object {
        private const val FILE_SUFFIX = ".desktop"

        private val preferences = localPreferences()

        var fileDir by preferences.with(FileHelper.defaultDesktopPath)

        fun fixFileSuffix(name: String): String {
            return if (name.endsWith(FILE_SUFFIX, true)) {
                name
            } else {
                name + FILE_SUFFIX
            }
        }

        private fun StringBuilder.addLine(vararg value: String): StringBuilder {
            value.forEach {
                append(it)
            }
            append("\n")
            return this
        }

        fun createValue(snapshot: Snapshot): String {
            return StringBuilder()
                .addLine("[Desktop Entry]")
                .addLine("Name=", snapshot.appName)
                .addLine("GenericName=", snapshot.genericName.ifBlank { snapshot.appName })
                .addLine("Comment=", snapshot.comment)
                .addLine("Version=", snapshot.version)
                .addLine("StartupNotify=", snapshot.startupNotify.toString().lowercase())
                .addLine("Terminal=", snapshot.terminal.toString().lowercase())
                .addLine("Type=", snapshot.appType.name)
                .addLine("Exec=", snapshot.execPath)
                .addLine("Icon=", snapshot.iconPath)
                .addLine("Categories=", getCategoriesValue(snapshot.categories))
                .toString()
        }

        private fun getCategoriesValue(categories: List<Categories>): String {
            val builder = StringBuilder()
            categories.forEach {
                builder.append(it.name)
                builder.append(";")
            }
            return builder.toString()
        }
    }

    fun createValue(): String {
        return StringBuilder()
            .addLine("[Desktop Entry]")
            .addLine("Name=", appName())
            .addLine("GenericName=", genericName().ifBlank { appName() })
            .addLine("Comment=", comment())
            .addLine("Version=", version())
            .addLine("StartupNotify=", startupNotify().toString().lowercase())
            .addLine("Terminal=", terminal().toString().lowercase())
            .addLine("Type=", appType().name)
            .addLine("Exec=", execPath())
            .addLine("Icon=", iconPath())
            .addLine("Categories=", getCategoriesValue())
            .toString()
    }

    fun getCategoriesValue(): String {
        val builder = StringBuilder()
        categories().forEach {
            builder.append(it.name)
            builder.append(";")
        }
        return builder.toString()
    }

    fun getSnapshot(): Snapshot {
        return Snapshot(
            fileName = fileName(),
            fileDir = fileDir(),
            appName = appName(),
            comment = comment(),
            genericName = genericName(),
            iconPath = iconPath(),
            execPath = execPath(),
            appType = appType(),
            categories = ArrayList(categories()),
            version = version(),
            startupNotify = startupNotify(),
            terminal = terminal()
        )
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

    class WriteOut {

        private var logCallback: ((String) -> Unit)? = null

        private var errorCallback: ((String) -> Unit)? = null

        fun onLog(callback: (String) -> Unit) {
            logCallback = callback
        }

        fun onError(callback: (String) -> Unit) {
            errorCallback = callback
        }

        fun log(value: String) {
            logCallback?.invoke(value)
        }

        fun error(value: String) {
            errorCallback?.invoke(value)
        }

    }

    data class Snapshot(
        val fileName: String,
        val fileDir: String,
        val appName: String,
        val comment: String,
        val genericName: String,
        val iconPath: String,
        val execPath: String,
        val appType: AppType,
        val categories: List<Categories>,
        val version: String,
        val startupNotify: Boolean,
        val terminal: Boolean,
    )

}