package io.github.afalabarce.compose.desktop.datasource.extensions

import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun <T>loggedTransaction(transactionBody: Transaction.() -> T) =
    transaction {
        if(System.getProperty("DEBUG")?.toBoolean() == true)
            addLogger(StdOutSqlLogger)

        return@transaction transactionBody
    }
