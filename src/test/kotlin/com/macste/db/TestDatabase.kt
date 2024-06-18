package com.macste.db

import org.jetbrains.exposed.sql.Database
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName


object TestDatabase {
    val TEST_USER = "user"
    val TEST_PASSWORD = "password"
    val TEST_DB = "testdb"

    val container: PostgreSQLContainer<*> by lazy {
        PostgreSQLContainer(DockerImageName.parse("postgres:14-alpine"))
            .withDatabaseName(TEST_DB)
            .withUsername(TEST_USER)
            .withPassword(TEST_PASSWORD)
            .also { it.start() }
    }

    fun getConnection() = Database.connect(
        url = container.getJdbcUrl(),
        user = container.username,
        password = container.password
    )
}