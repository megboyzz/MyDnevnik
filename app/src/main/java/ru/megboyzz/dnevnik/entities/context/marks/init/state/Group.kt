package ru.megboyzz.dnevnik.entities.context.marks.init.state

data class Group(
    val groupId: String,
    val groupName: String,
    val reportingPeriodNotSet: Boolean,
    val periodTabName: String
)
