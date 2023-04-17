package ru.megboyzz.dnevnik.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.megboyzz.dnevnik.db.converters.DataStructureConverter
import ru.megboyzz.dnevnik.entities.context.marks.init.state.MarksContext
import ru.megboyzz.dnevnik.entities.response.UserContext

@Entity
@TypeConverters(DataStructureConverter::class)
data class GlobalUserContext(
    @PrimaryKey
    val id: Int = 0,
    val apiUserContext: UserContext, // Обычный контекст из API
    val marksInitStateUserContext: MarksContext //window.__MARKS__INITIAL__STATE__
)
