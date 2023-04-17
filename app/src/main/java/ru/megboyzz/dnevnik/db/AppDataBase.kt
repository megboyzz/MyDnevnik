package ru.megboyzz.dnevnik.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.megboyzz.dnevnik.db.dao.CredentialsDao
import ru.megboyzz.dnevnik.db.dao.GlobalUserContextDao
import ru.megboyzz.dnevnik.entities.db.Credentials
import ru.megboyzz.dnevnik.entities.db.GlobalUserContext

@Database(
    entities = [Credentials::class, GlobalUserContext::class],
    version = 1
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun credentialsDao(): CredentialsDao
    abstract fun globalUserContextDao(): GlobalUserContextDao

}