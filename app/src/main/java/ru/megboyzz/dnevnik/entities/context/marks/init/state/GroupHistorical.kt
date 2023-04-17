package ru.megboyzz.dnevnik.entities.context.marks.init.state

data class GroupHistorical(
    val id: String,
    val name: String,
    val studyYear: Int,
    val periods: List<Period>,
    val periodTabName: String
)
