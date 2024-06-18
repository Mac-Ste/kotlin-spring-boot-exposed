package com.macste.db

import com.macste.db.tables.TestTable
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class PostgresTest {
    @Test
    fun x() {
        var result = ""
        var removed = false

        transaction(TestDatabase.getConnection()) {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(TestTable)

            val insertedId = TestTable.insertAndGetId {
                it[firstName] = "John"
                it[lastName] = "Doe"
            }.value

            result = TestTable.selectAll().where {
                TestTable.id eq insertedId
            }.first().let { "${it[TestTable.firstName]} ${it[TestTable.lastName]}" }

            removed = TestTable.deleteWhere { id eq insertedId } > 0

        }

        result shouldBe "John Doe"
        removed shouldBe true
    }
}