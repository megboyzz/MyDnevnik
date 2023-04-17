package ru.megboyzz.dnevnik.entities.context.marks.init.state


data class UserContext(
    val userId: String,
    val role: String,
    val schoolMemberships: List<SchoolMembership>,
)
