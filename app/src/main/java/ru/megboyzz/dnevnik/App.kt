package ru.megboyzz.dnevnik

import android.app.Application
import androidx.room.Room
import ru.megboyzz.dnevnik.db.CredentialsDataBase

class App : Application() {

    lateinit var database: CredentialsDataBase
        private set

    override fun onCreate() {

        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, CredentialsDataBase::class.java, "database")
            .build()
    }

    companion object {
        var instance: App? = null
    }
}