package io.github.afalabarce.compose.desktop.datasource.preferences

import io.github.afalabarce.compose.desktop.models.engines.DatabaseEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.nio.file.*
import java.util.*

internal class PreferencesStoreImpl: PreferencesStore, CoroutineScope by CoroutineScope(Dispatchers.IO) {
    private val propertiesPath: String = System.getProperty("user.dir")
    private val propertiesWatcherKey: WatchKey
    private val propertiesWatcher: WatchService = FileSystems.getDefault().newWatchService()
    private val propertiesFile: File

    init {
        val absolutePropertiesPath = "${this.propertiesPath}${File.separator}settings.properties"
        propertiesFile = File(absolutePropertiesPath)

        if (!propertiesFile.exists())
            propertiesFile.createNewFile()

        propertiesWatcherKey = File(this.propertiesPath).toPath().register(
            propertiesWatcher,
            StandardWatchEventKinds.ENTRY_MODIFY,
        )
    }

    //region private properties and methods

    private lateinit var propertiesStore: Properties
    private val properties: Properties
        get() {
            if (!::propertiesStore.isInitialized) {
                loadProperties()
            }

            return propertiesStore
        }

    private fun loadProperties() {
        propertiesStore = Properties().apply {
            load(propertiesFile.inputStream())
            propertiesFile.inputStream().close()
        }
    }

    private val Properties.databaseEngine
        get() = when(this.getOrDefault("DatabaseType", -1) as Int){
            0 -> DatabaseEngine.PostgreSql(
                serverHost = this.getOrDefault("PostgreSqlHost", "") as String,
                port = this.getOrDefault("PostgreSqlPort", 5432) as Int,
                userName = this.getOrDefault("PostgreSqlUsername", "") as String,
                password = this.getOrDefault("PostgreSqlPassword", "") as String,
                databaseName = this.getOrDefault("PostgreSqlDatabase", "") as String,
            )
            1 -> DatabaseEngine.MySql(
                serverHost = this.getOrDefault("MySqlHost", "") as String,
                port = this.getOrDefault("MySqlPort", 3306) as Int,
                userName = this.getOrDefault("MySqlUsername", "") as String,
                password = this.getOrDefault("MySqlPassword", "") as String,
                databaseName = this.getOrDefault("MySqlDatabase", "") as String,
            )
            2 -> DatabaseEngine.SqlServer(
                serverInstance = this.getOrDefault("SqlServerInstance", "") as String,
                port = this.getOrDefault("SqlServerPort", 1433) as Int,
                userName = this.getOrDefault("SqlServerUsername", "") as String,
                password = this.getOrDefault("SqlServerPassword", "") as String,
                databaseName = this.getOrDefault("SqlServerDatabase", "") as String,
            )
            3 -> DatabaseEngine.Sqlite(
                databasePath = this.getOrDefault("SqlitePath", "") as String,
            )
            4 -> DatabaseEngine.H2(
                databasePath = this.getOrDefault("H2Path", "") as String,
            )

            else -> DatabaseEngine.None
        }

    private fun Properties.applyChanges(){
        val absolutePropertiesPath = "${this@PreferencesStoreImpl.propertiesPath}${File.separator}settings.properties"
        val propertiesOutStream = FileOutputStream(absolutePropertiesPath)
        this.store(propertiesOutStream, null)
        propertiesOutStream.close()

    }
    private suspend fun folderWatcher(onWatch: suspend (List<File>) -> Unit) {
        withContext(Dispatchers.IO) {
            while (true) {
                val key = this@PreferencesStoreImpl.propertiesWatcher.take()
                key.pollEvents().filter { evt ->
                    listOf(StandardWatchEventKinds.ENTRY_MODIFY).any { x ->
                        x == evt.kind()
                    } && evt is WatchEvent<*>
                }.forEach { _ ->
                    onWatch(listOf(propertiesFile))
                    key.reset()
                    return@forEach
                }
            }
        }
    }

    //endregion

    override val databaseEngine: Flow<DatabaseEngine>
        get() = channelFlow {
            this@PreferencesStoreImpl.folderWatcher { files ->
                if (files.any { x -> x.name == propertiesFile.name })
                    send(properties.databaseEngine)
            }
        }
    override val firstLoad: Flow<Boolean>
        get() = channelFlow {
            this@PreferencesStoreImpl.folderWatcher { files ->
                if (files.any { x -> x.name == propertiesFile.name }) {
                    loadProperties()
                    send(
                        element = properties.getProperty("FirstLoad", "true")
                            .lowercase() == "true"
                    )
                }
            }
        }

    override suspend fun setDatabaseEngine(engine: DatabaseEngine) {
        when (engine){
            is DatabaseEngine.PostgreSql -> {
                properties.setProperty("DatabaseType", "0")
                properties.setProperty("PostgreSqlHost", engine.serverHost)
                properties.setProperty("PostgreSqlPort", engine.port.toString())
                properties.setProperty("PostgreSqlDatabase", engine.databaseName)
                properties.setProperty("PostgreSqlUsername", engine.userName)
                properties.setProperty("PostgreSqlPassword", engine.password)
            }
            is DatabaseEngine.MySql -> {
                properties.setProperty("DatabaseType", "1")
                properties.setProperty("MySqlHost", engine.serverHost)
                properties.setProperty("MySqlPort", engine.port.toString())
                properties.setProperty("MySqlDatabase", engine.databaseName)
                properties.setProperty("MySqlUsername", engine.userName)
                properties.setProperty("MySqlPassword", engine.password)
            }
            is DatabaseEngine.SqlServer -> {
                properties.setProperty("DatabaseType", "2")
                properties.setProperty("SqlServerInstance", engine.serverInstance)
                properties.setProperty("SqlServerPort", engine.port.toString())
                properties.setProperty("SqlServerDatabase", engine.databaseName)
                properties.setProperty("SqlServerUsername", engine.userName)
                properties.setProperty("SqlServerPassword", engine.password)
            }
            is DatabaseEngine.Sqlite -> {
                properties.setProperty("DatabaseType", "3")
                properties.setProperty("SqlitePath", engine.databasePath)
            }
            is DatabaseEngine.H2 -> {
                properties.setProperty("DatabaseType", "4")
                properties.setProperty("H2Path", engine.databasePath)
            }
            else -> { }
        }

        properties.applyChanges()
    }

    override suspend fun setFirstLoad(isFirstLoad: Boolean) {
        properties.setProperty("FirstLoad", isFirstLoad.toString())
        properties.applyChanges()
    }

}