package ru.megboyzz.dnevnik.entities.db

import androidx.room.Entity

@Entity
data class Credentials(
    val token: String,
    val cookies: Map<String, String>
)
