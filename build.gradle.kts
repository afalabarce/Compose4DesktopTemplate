import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "io.github.afalabarce.desktop.compose"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val coroutinesVersion = "1.6.4"
        val materialIconsVersion = "1.1.0-alpha04"
        val composeAnimationVersion = "1.5.0-alpha02"
        val constraintLayoutComposeVersion = "1.1.0-alpha09"
        val composeRuntimeVersion = "1.3.3"
        val composeUiVersion = "1.5.0-alpha02"

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)

                implementation(project(mapOf("path" to ":models")))
                implementation(project(mapOf("path" to ":domain")))

                implementation("org.jetbrains.compose.material:material-icons-extended-desktop:$materialIconsVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

                // Koin dependencies for automatic dependency provisioning
                implementation("io.insert-koin:koin-core:3.3.3")

                implementation("androidx.compose.animation:animation:$composeAnimationVersion")
                implementation("androidx.compose.animation:animation-core:$composeAnimationVersion")
                implementation("androidx.compose.animation:animation-graphics:$composeAnimationVersion")
                implementation("androidx.constraintlayout:constraintlayout-compose:$constraintLayoutComposeVersion")
                implementation("androidx.compose.runtime:runtime:$composeRuntimeVersion")
                implementation("androidx.compose.runtime:runtime-saveable:$composeRuntimeVersion")
                implementation("androidx.compose.ui:ui:$composeUiVersion")
                implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")

            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "$group.MainKt"
        fromFiles(project.fileTree("libs/") { include("**/*.jar") })

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Compose4Desktop Template App"
            description = "Template App for creating Desktop Multiplatform apps with Compose"
            packageVersion = "1.0.0"
            includeAllModules = true
            javaHome = "/Library/Java/JavaVirtualMachines/jdk-17.0.2.jdk"
            vendor = "Antonio Fdez. Alabarce"
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
            windows {
                iconFile.set(project.file("src/jvmMain/resources/mipmap/ic_launcher.ico"))
                dirChooser = true
                //javaHome = "C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2022.2.2\\jbr"
                menuGroup = "Compose4Dektop Template App"
            }

            macOS{
                packageBuildVersion = "1.0.0"
                bundleID = "$group.MainKt"
                dockName = "Compose4Desktop Template App"
                javaHome = "/Library/Java/JavaVirtualMachines/jdk-17.0.2.jdk"
                iconFile.set(project.file("src/jvmMain/resources/mipmap/ic_launcher.icns"))
                mainClass = "$group.MainKt"
                appCategory = "public.app-category.developer-tools"
            }
        }
    }
}
