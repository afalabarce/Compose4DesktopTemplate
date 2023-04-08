package io.github.afalabarce.compose.desktop.models.engines

sealed class DatabaseEngine {

    object None: DatabaseEngine()
    data class PostgreSql(
        val serverHost: String,
        val port: Int = 5432,
        val userName: String,
        val password: String,
        val databaseName: String
    ): DatabaseEngine()

    data class MySql(
        val serverHost: String,
        val port: Int = 3306,
        val userName: String,
        val password: String,
        val databaseName: String
    ): DatabaseEngine()

    data class SqlServer(
        val serverInstance: String,
        val port: Int = 1433,
        val userName: String,
        val password: String,
        val databaseName: String
    ): DatabaseEngine()

    data class Sqlite(
        val databasePath: String
    ): DatabaseEngine()

    data class H2(
        val databasePath: String
    ): DatabaseEngine()
}
