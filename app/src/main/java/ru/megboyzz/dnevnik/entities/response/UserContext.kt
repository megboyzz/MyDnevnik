package ru.megboyzz.dnevnik.entities.response

import ru.megboyzz.dnevnik.entities.context.PersonContext
import ru.megboyzz.dnevnik.entities.context.UserInfo


data class UserContext(
    val info: UserInfo,
    val contextPersons: List<PersonContext>
)
