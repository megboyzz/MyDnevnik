package ru.megboyzz.dnevnik.entities.context

data class SchoolInfo(
    val id: Long,
    val name: String,
    val avatarUrl: String,
    val type: SchoolType
)
