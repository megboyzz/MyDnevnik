package ru.megboyzz.dnevnik

import android.app.Application
import androidx.room.Room
import ru.megboyzz.dnevnik.db.AppDataBase

class App : Application() {

    lateinit var database: AppDataBase
        private set

    override fun onCreate() {

        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDataBase::class.java, "database")
            .build()
    }

    companion object {
        var instance: App? = null
    }
}