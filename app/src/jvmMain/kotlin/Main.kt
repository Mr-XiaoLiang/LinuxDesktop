import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lollipoppp.desktop.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "LinuxDesktop",
    ) {
        App()
    }
}
