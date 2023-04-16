package ru.megboyzz.dnevnik.entities.context

data class UserInfo(
    val sex: String,
    val roles: List<String>,
    val userId: Long,
    val personId: Long,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val avatarUrl: String,
)
