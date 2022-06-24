package com.lollipoppp.desktop.utils

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter

object DesktopWrite {

    fun write(snapshot: DesktopFile.Snapshot, callback: DesktopFile.WriteOut.() -> Unit) {
        val writeOut = DesktopFile.WriteOut()
        callback(writeOut)
        val log = writeOut::log
        val error = writeOut::error

        try {
            doWrite(snapshot, log, error)
        } catch (e: Throwable) {
            error("发生异常了")
            val outputStream = ByteArrayOutputStream()
            val writer = PrintWriter(outputStream)
            e.printStackTrace(writer)
            writer.flush()
            val errorInfo = outputStream.toString()
            error(errorInfo)
        }

    }

    private fun doWrite(snapshot: DesktopFile.Snapshot, log: (String) -> Unit, error: (String) -> Unit) {
        log("写入文件路径: ${snapshot.fileDir}")

        if (snapshot.fileDir.isEmpty()) {
            error("文件路径不能为空")
            return
        }

        val fileName = DesktopFile.fixFileSuffix(snapshot.fileName)
        log("写入文件名: $fileName")

        if (fileName.isEmpty()) {
            error("文件名不能为空")
            return
        }

        if (fileName.contains(" ")) {
            error("文件名不能包含空格")
            return
        }

        log("应用名称: ${snapshot.appName}")
        if (snapshot.appName.isEmpty()) {
            error("应用名不能为空")
            return
        }

        log("可执行文件路径为: ${snapshot.execPath}")
        if (snapshot.execPath.isEmpty()) {
            error("应用文件路径不能为空")
            return
        }

        log("应用图标文件路径为: ${snapshot.iconPath}")
        if (snapshot.iconPath.isEmpty()) {
            error("应用图标路径不能为空")
            return
        }

        log("开始写入, 写入内容为")
        log("###############################")
        val value = DesktopFile.createValue(snapshot)
        log(value)

        val dirFile = File(snapshot.fileDir)
        if (!dirFile.exists()) {
            log("文件夹不存在, 尝试创建")
            dirFile.mkdirs()
        }
        if (dirFile.isFile) {
            error("文件夹路径为文件, 为保证安全, 请手动删除: ${dirFile.absolutePath}")
            return
        }

        val launcherFile = File(dirFile, fileName)
        if (launcherFile.exists()) {
            error("发现文件已经存在, 为保证安全, 请手动删除")
            return
        }
        log("正在创建文件")
        launcherFile.createNewFile()
        log("正在创建输出流")
        val outputStream = FileOutputStream(launcherFile)
        log("正在转码")
        val byteArray = value.toByteArray(Charsets.UTF_8)
        log("内容长度: ${byteArray.size}byte")
        log("正在写入")
        outputStream.write(byteArray, 0, byteArray.size)
        outputStream.flush()
        outputStream.close()
        log("写入完毕，请前往文件夹查看")
        log("写入文件路径为: ${launcherFile.absolutePath}")
    }

}