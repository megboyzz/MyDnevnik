package ru.megboyzz.dnevnik.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CookiesConverter {

    private val gson = Gson()
    private val mapType = object : TypeToken<Map<String, String>>() {}.type

    @TypeConverter
    fun fromMap(cookies: Map<String, String>): String = gson.toJson(cookies)

    @TypeConverter
    fun toMap(savedCookies: String): Map<String, String> = gson.fromJson(savedCookies, mapType)

}