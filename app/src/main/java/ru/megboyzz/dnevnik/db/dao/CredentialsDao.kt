package ru.megboyzz.dnevnik.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.megboyzz.dnevnik.entities.db.Credentials

@Dao
interface CredentialsDao {

    @Query("SELECT EXISTS(SELECT * FROM Credentials WHERE id = 0)")
    fun credentialsIsExists(): Boolean
    @Query("SELECT * FROM Credentials WHERE id = 0")
    fun getCredentials(): Credentials

    @Query("DELETE FROM Credentials WHERE id = 0")
    fun deleteCredentials()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setCredentials(profile: Credentials)

}