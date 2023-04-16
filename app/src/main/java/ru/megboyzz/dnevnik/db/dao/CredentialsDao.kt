package ru.megboyzz.dnevnik.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.megboyzz.dnevnik.entities.db.Credentials

interface CredentialsDao {

    @Query("SELECT * FROM Credentials WHERE id = 1")
    fun getCredentials(): LiveData<Credentials>

    @Query("DELETE FROM Credentials WHERE id = 1")
    fun deleteCredentials()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setCredentials(profile: Credentials)

}