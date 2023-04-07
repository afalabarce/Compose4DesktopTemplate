package io.github.afalabarce.compose.desktop.datasource.engines

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

sealed class DatabaseEngine {
    abstract fun getConnection(): Database
    data class PostgreSql(
        val serverHost: String,
        val port: Int = 5432,
        val userName: String,
        val password: String,
        val databaseName: String
    ): DatabaseEngine(){
        override fun getConnection(): Database = Database.connect(
            url = "jdbc:postgresql://${this.serverHost}:${this.port}/${this.databaseName}",
            driver ="org.postgresql.Driver",
            user = this.userName,
            password = this.password
        )
    }

    data class MySql(
        val serverHost: String,
        val port: Int = 3306,
        val userName: String,
        val password: String,
        val databaseName: String
    ): DatabaseEngine(){
        override fun getConnection(): Database {
            val settings = HikariConfig().apply {
                jdbcUrl         = "jdbc:mysql://${this@MySql.serverHost}:${this@MySql.port}/${this@MySql.databaseName}"
                driverClassName = "com.mysql.cj.jdbc.Driver"
                username        = this@MySql.userName
                password        = this@MySql.password
                maximumPoolSize = 10
            }

            return Database.connect(HikariDataSource(settings))
        }
    }

    data class SqlServer(
        val serverInstance: String,
        val port: Int = 1433,
        val userName: String,
        val password: String,
        val databaseName: String
    ): DatabaseEngine(){
        override fun getConnection(): Database =
            Database.connect(
                url = "jdbc:sqlserver://${this.serverInstance}:${this.port};databaseName=${this.databaseName}",
                driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                user = this.userName,
                password = this.password
            )
    }

    data class Sqlite(
        val databasePath: String
    ): DatabaseEngine(){
        override fun getConnection(): Database = Database.connect("jdbc:sqlite:${this.databasePath}", "org.sqlite.JDBC")
    }

    data class H2(
        val databasePath: String
    ): DatabaseEngine(){
        override fun getConnection(): Database = Database.connect("jdbc:h2:${this.databasePath}", "org.h2.Driver")
    }
}
