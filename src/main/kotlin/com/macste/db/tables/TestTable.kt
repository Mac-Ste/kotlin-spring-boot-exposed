package com.macste.db.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object TestTable: LongIdTable() {
    val firstName = varchar("first_name",255)
    val lastName = varchar("last_name",255)
}