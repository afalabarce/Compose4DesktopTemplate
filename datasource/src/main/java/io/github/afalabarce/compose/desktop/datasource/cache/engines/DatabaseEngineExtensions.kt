package io.github.afalabarce.compose.desktop.datasource.cache.engines

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.afalabarce.compose.desktop.models.engines.DatabaseEngine
import org.jetbrains.exposed.sql.Database

fun DatabaseEngine.PostgreSql.getConnection(): Database = Database.connect(
    url = "jdbc:postgresql://${this.serverHost}:${this.port}/${this.databaseName}",
    driver ="org.postgresql.Driver",
    user = this.userName,
    password = this.password
)

fun DatabaseEngine.MySql.getConnection(): Database {
    val settings = HikariConfig().apply {
        jdbcUrl         = "jdbc:mysql://${this@getConnection.serverHost}:${this@getConnection.port}/${this@getConnection.databaseName}"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username        = this@getConnection.userName
        password        = this@getConnection.password
        maximumPoolSize = 10
    }

    return Database.connect(HikariDataSource(settings))
}

fun DatabaseEngine.SqlServer.getConnection(): Database =
    Database.connect(
        url = "jdbc:sqlserver://${this.serverInstance}:${this.port};databaseName=${this.databaseName}",
        driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver",
        user = this.userName,
        password = this.password
    )

fun DatabaseEngine.Sqlite.getConnection(): Database = Database.connect("jdbc:sqlite:${this.databasePath}", "org.sqlite.JDBC")

fun DatabaseEngine.H2.getConnection(): Database = Database.connect("jdbc:h2:${this.databasePath}", "org.h2.Driver")