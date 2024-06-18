package com.macste.db

import com.macste.db.tables.TestTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class SchemaInitializer: ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        SchemaUtils.create(TestTable)
    }
}