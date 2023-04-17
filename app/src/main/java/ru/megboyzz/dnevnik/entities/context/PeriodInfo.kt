package ru.megboyzz.dnevnik.entities.context

data class PeriodInfo(
    val id: Long,
    val number: Short,
    val type: GroupType,
    val dateStart: Long,
    val dateFinish: Long,
    val studyYear: Short,
    val isCurrent: Boolean
)
