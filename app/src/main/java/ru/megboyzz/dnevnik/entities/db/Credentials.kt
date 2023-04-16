package ru.megboyzz.dnevnik.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.megboyzz.dnevnik.db.converters.CookiesConverter

@Entity
@TypeConverters(CookiesConverter::class)
data class Credentials(
    @PrimaryKey
    val id: Int = 0,
    val token: String,
    val cookies: Map<String, String>,
    val rememberMe: Boolean
)
