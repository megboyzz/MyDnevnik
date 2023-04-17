package ru.megboyzz.dnevnik.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.megboyzz.dnevnik.entities.db.GlobalUserContext

@Dao
interface GlobalUserContextDao {

    @Query("SELECT EXISTS(SELECT * FROM GlobalUserContext WHERE id = 0)")
    fun globalUserContextIsExists(): Boolean

    @Query("SELECT * FROM GlobalUserContext WHERE id = 0")
    fun getGlobalUserContext(): GlobalUserContext

    @Query("DELETE FROM GlobalUserContext WHERE id = 0")
    fun deleteGlobalUserContext()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setGlobalUserContext(profile: GlobalUserContext)

}