import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.lollipoppp"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenLocal { "file:///Users/lollipop/Develop/Java/LocalMaven" }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

dependencies {
//    implementation("com.lollipoppp.vector:VectorClip:1.0-SNAPSHOT")
}

/**
 * 相关文档
 * https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Native_distributions_and_local_execution
 */
compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "linuxDesktop"
            packageVersion = "1.0.0"
            macOS {
                // 设置启动图标
                iconFile.set(project.file("icon.icns"))
            }
            windows {
                // 设置启动图标
                iconFile.set(project.file("icon.ico"))
            }
            linux {
                // 设置启动图标
                iconFile.set(project.file("icon.png"))
            }
        }
    }
}
