package ru.megboyzz.dnevnik.entities.context

data class PersonContext(
    val sex: String,
    val userId: Long,
    val personId: Long,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val avatarUrl: String,
    val school: SchoolInfo,
    val group: GroupInfo,
    val reportingPeriodGroup: PeriodGroup
)
