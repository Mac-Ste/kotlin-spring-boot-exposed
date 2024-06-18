package com.macste.controllers

import com.macste.db.tables.TestTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/debug")
class DebugController {
    @GetMapping("/check")
    fun healthCheck() = HealthCheckResponse("UP")

    @GetMapping("/testDb")
    fun testDb(): DatabaseTestResponse {
        var result = ""
        var removed = false
        transaction {
            addLogger(StdOutSqlLogger)
            val insertedId = TestTable.insertAndGetId {
                it[firstName] = "John"
                it[lastName] = "Doe"
            }.value

            result = TestTable.selectAll().where {
                TestTable.id eq insertedId
            }.first().let { "${it[TestTable.firstName]} ${it[TestTable.lastName]}" }

            removed = TestTable.deleteWhere { id eq insertedId } > 0
        }
        return DatabaseTestResponse(
            result, removed
        )
    }

    data class HealthCheckResponse(
        val status: String
    )

    data class DatabaseTestResponse(
        val result: String,
        val removed: Boolean
    )
}