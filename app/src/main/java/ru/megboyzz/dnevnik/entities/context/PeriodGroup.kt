package ru.megboyzz.dnevnik.entities.context

data class PeriodGroup(
    val id: Long,
    val type: GroupType, //Обычно равен "Quarter", то есть Четверть, но как будет семестр у них????
    val periods: List<PeriodInfo>
)
