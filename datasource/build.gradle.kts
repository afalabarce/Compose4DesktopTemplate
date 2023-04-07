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

val ktorVersion = "2.2.4"
val coroutinesVersion = "1.6.4"
val exposedVersion = "0.40.1"

dependencies{
    implementation(project(mapOf("path" to ":models")))

    // Koin dependencies for automatic dependency provisioning
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")
    implementation("io.insert-koin:koin-core:3.3.3")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // kTorClient for consuming connected apis
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-java:$ktorVersion")
    implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    // Exposed (https://github.com/JetBrains/Exposed) for consuming data from databases (embeded or not), with JPA, JDBC, etc
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    //Exposed jdbc and jpa drivers
    implementation ("com.zaxxer:HikariCP:3.4.2")
    implementation("com.h2database:h2:2.1.214")
    implementation("com.microsoft.sqlserver:mssql-jdbc:6.4.0.jre7")
    implementation("mysql:mysql-connector-java:8.0.19")
    implementation("org.postgresql:postgresql:42.2.2")
    implementation("org.xerial:sqlite-jdbc:3.30.1")
    // Oracle jdbc-driver should be obtained from Oracle maven repo: https://blogs.oracle.com/dev2dev/get-oracle-jdbc-drivers-and-ucp-from-oracle-maven-repository-without-ides

    // Other optional Exposed modules
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-money:$exposedVersion")
    // for logging (StdOutSqlLogger), see http://www.slf4j.org/codes.html#StaticLoggerBinder
    implementation("org.slf4j:slf4j-nop:1.7.30")
}