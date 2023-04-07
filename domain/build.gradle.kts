plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    google()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

val coroutinesVersion = "1.6.4"

dependencies{
    implementation(project(mapOf("path" to ":models")))
    implementation(project(mapOf("path" to ":data")))
    // Koin dependencies for automatic dependency provisioning
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")
    implementation("io.insert-koin:koin-core:3.3.3")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}