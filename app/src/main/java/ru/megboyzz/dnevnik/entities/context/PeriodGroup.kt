package ru.megboyzz.dnevnik.entities.context

data class PeriodGroup(
    val id: Long,
    val type: String, //Обычно равен "Quarter", то есть Четверть, но как будет семестр у них????
    val periods: List<PeriodInfo>
)
