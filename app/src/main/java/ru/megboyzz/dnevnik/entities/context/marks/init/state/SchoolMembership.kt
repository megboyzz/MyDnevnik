package ru.megboyzz.dnevnik.entities.context.marks.init.state

data class SchoolMembership(
    val personId: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val schoolId: String,
    val schoolName: String,
    val isOdo: Boolean,
    val isTermReportEnabled: Boolean,
    val studyYear: Int,
    val schoolType: SchoolType,
    val groups: List<Group>,
    val holidays: List<String>,
    val groupHistory: List<GroupHistorical>
)
