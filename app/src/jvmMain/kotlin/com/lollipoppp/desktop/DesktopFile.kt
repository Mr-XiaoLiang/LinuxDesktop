package com.lollipoppp.desktop

object DesktopFile {

    fun createValue(
            appName: String,
            comment: String,
            genericName: String,
            iconPath: String,
            execPath: String,
            appType: AppType,
            categories: List<Categories>,
            version: String,
            startupNotify: Boolean,
            terminal: Boolean,
    ): String {
        return StringBuilder()
                .addLine("[Desktop Entry]")
                .addLine("Name=", appName)
                .addLine("GenericName=", genericName.ifBlank { appName })
                .addLine("Comment=", comment)
                .addLine("Version=", version)
                .addLine("StartupNotify=", startupNotify.toString().lowercase())
                .addLine("Terminal=", terminal.toString().lowercase())
                .addLine("Type=", appType.name)
                .addLine("Exec=", execPath)
                .addLine("Icon=", iconPath)
                .addLine("Categories=", getCategoriesValue(categories))
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

}