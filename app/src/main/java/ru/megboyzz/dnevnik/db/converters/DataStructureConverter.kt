package ru.megboyzz.dnevnik.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.megboyzz.dnevnik.entities.context.marks.init.state.MarksContext
import ru.megboyzz.dnevnik.entities.response.UserContext

class DataStructureConverter {

    val gson = Gson()

    @TypeConverter
    fun fromUserContext(userContext: UserContext): String = gson.toJson(userContext)

    @TypeConverter
    fun toUserContext(savedUserContext: String): UserContext = gson.fromJson(savedUserContext, UserContext::class.java)

    @TypeConverter
    fun fromMarksContext(marksContext: MarksContext): String = gson.toJson(marksContext)

    @TypeConverter
    fun toMarksContext(savedMarksContext: String): MarksContext = gson.fromJson(savedMarksContext, MarksContext::class.java)

}