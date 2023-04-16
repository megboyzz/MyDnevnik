package ru.megboyzz.dnevnik.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.megboyzz.dnevnik.db.dao.CredentialsDao
import ru.megboyzz.dnevnik.entities.db.Credentials

@Database(
    entities = [Credentials::class],
    version = 1
)
abstract class CredentialsDataBase: RoomDatabase() {

    abstract fun credentialsDao(): CredentialsDao

}